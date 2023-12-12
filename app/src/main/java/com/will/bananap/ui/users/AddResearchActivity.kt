package com.will.bananap.ui.users


import com.will.bananap.R
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class AddResearchActivity : AppCompatActivity() {


    private lateinit var editTitle: EditText
    private lateinit var editArea: EditText
    private lateinit var editDescription: EditText
    private lateinit var editPdfUrl: EditText
    private lateinit var btnAddResearch: Button

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_research)

        editTitle = findViewById(R.id.editTitle)
        editArea = findViewById(R.id.editArea)
        editDescription = findViewById(R.id.editDescription)
        editPdfUrl = findViewById(R.id.editPdfUrl)
        btnAddResearch = findViewById(R.id.btnAddResearch)


        btnAddResearch.setOnClickListener {
            // Obtener los valores de los campos
            val title = editTitle.text.toString()
            val area = editArea.text.toString()
            val description = editDescription.text.toString()
            val pdfUrl = editPdfUrl.text.toString()

            // Verificar que los campos no estén vacíos
            if (title.isNotEmpty() && area.isNotEmpty() && description.isNotEmpty() && pdfUrl.isNotEmpty()) {
                // Crear un mapa con los datos de la investigación
                val researchData = hashMapOf(
                    "title" to title,
                    "areaOfInterest" to area,
                    "briefDescription" to description,
                    "pdfURL" to pdfUrl,
                    "images" to emptyList<String>(),  // Ajusta según tu estructura
                    "conclusions" to "",
                    "recommendations" to "",
                    "userId" to auth.uid,  // Agregar el ID del usuario autenticado
                )
                // Agregar la nueva investigación a Firestore
                db.collection("researches")
                    .add(researchData)
                    .addOnSuccessListener { documentReference ->
                        // Éxito al agregar la investigación
                        // Puedes realizar acciones adicionales aquí si es necesario
                        showToast("Agregado Correctamente")
                        finish()  // Cerrar la actividad después de agregar exitosamente
                    }
                    .addOnFailureListener { e ->
                        // Manejar errores al agregar la investigación
                    }
            } else {
                // Mostrar un mensaje de error si algún campo está vacío
                // Puedes personalizar este mensaje según tus necesidades
                showToast("Todos los campos son obligatorios")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message.toString(), Toast.LENGTH_LONG).show()
    }


}
