package com.will.bananap.ui.users

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.will.bananap.R
import com.will.bananap.entities.cls_Comentarios


class ResearchDetailActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var areaTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var viewPager: ViewPager2

    private lateinit var newCommentEditText: EditText
    private lateinit var sendCommentButton: Button
    private lateinit var commentsRecyclerView: RecyclerView

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_research_detail)

        titleTextView = findViewById(R.id.titleTextView)
        areaTextView = findViewById(R.id.areaTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        viewPager = findViewById(R.id.viewPager)

        newCommentEditText = findViewById(R.id.newCommentEditText)
        sendCommentButton = findViewById(R.id.sendCommentButton)
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView)

        val selectedTitle = intent.getStringExtra("selectedTitle")
        val projectId = intent.getStringExtra("projectId")


        // Realizar consulta a Firestore para obtener los detalles
        db.collection("datosInvestigacion")
            .document(projectId!!)
            .get()
            .addOnSuccessListener { document ->
                // Mostrar los detalles en la interfaz de usuario
                titleTextView.text = document.getString("titulo")
                areaTextView.text = document.getString("area")
                descriptionTextView.text = document.getString("descripcion")

                // Obtener la lista de imágenes desde Firestore
                val imagesList: List<String> = document.get("urlImages") as List<String>

                // Configurar el adaptador del ViewPager2
                val imageAdapter = ImagePagerAdapter(imagesList)
                viewPager.adapter = imageAdapter
            }
            .addOnFailureListener { exception ->
                // Manejar errores de la consulta
            }


        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        commentsRecyclerView.layoutManager = layoutManager

        sendCommentButton.setOnClickListener {
            sendComment()
        }
        // Cargar comentarios desde Firestore
        loadComments()

    }


    private fun sendComment() {
        val projectId = intent.getStringExtra("projectId")
        val commentText = newCommentEditText.text.toString()

        if (projectId != null && commentText.isNotEmpty()) {
            // Agregar el nuevo comentario a Firestore
            val commentData = hashMapOf(
                "userId" to  auth.currentUser?.uid.toString(),
                "texto" to commentText.toString(),
                "datosInvestigacionId" to projectId.toString(),
                "timestamp" to System.currentTimeMillis().toString()
            )

            db.collection("comentarios")
                .add(commentData)
                .addOnSuccessListener { documentReference ->
                    // Limpiar el EditText después de enviar el comentario
                    newCommentEditText.text.clear()
                    // Recargar la lista de comentarios
                    loadComments()
                }
                .addOnFailureListener { exception ->
                    // Manejar errores al enviar el comentario
                }
        }
    }


    private fun loadComments() {
        val projectId = intent.getStringExtra("projectId")

        if (projectId.isNullOrBlank()) {
            Log.e(TAG, "El projectId es nulo o vacío")
            return
        }

        // Obtener todos los comentarios sin orden específico
        db.collection("comentarios")
            .whereEqualTo("datosInvestigacionId", projectId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val commentsList = task.result?.documents?.mapNotNull { document ->
                        document.toObject(cls_Comentarios::class.java)
                    } ?: emptyList()

                    Log.d(TAG, "Comentarios cargados exitosamente. Número de comentarios: ${commentsList.size}")

                    val commentsAdapter = CommentsAdapter(commentsList)
                    commentsRecyclerView.adapter = commentsAdapter
                } else {
                    Log.e(TAG, "Error al cargar comentarios", task.exception)
                    // Manejar errores al cargar los comentarios (puedes mostrar un Toast u otra acción para notificar al usuario)
                }
            }
    }


}
