package com.will.bananap.ui.users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.will.bananap.R
import kotlin.math.log

class ResearchActivity : AppCompatActivity() {

    private lateinit var researchListView: ListView
    private lateinit var researchList: MutableList<String>
    private lateinit var fabButton: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_research)

        val filterText = findViewById<TextView>(R.id.filterText)
        filterText.setOnClickListener { showFilterMenu(it) }


        researchListView = findViewById(R.id.researchListView)
        researchList = mutableListOf()

        // Configurar el adaptador para la lista
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, researchList)
        researchListView.adapter = adapter

        // Realizar la consulta a Firestore
        db.collection("datosInvestigacion")
            .get()
            .addOnSuccessListener { result ->
                val documents = result.documents

                for (document in documents) {
                    // Asegúrate de que el campo sea "titulo" y no "titulo" (asegúrate de que la estructura coincida)
                    val titulo = document.getString("titulo")
                    researchList.add(titulo ?: "")
                }

                adapter.notifyDataSetChanged()

                researchListView.setOnItemClickListener { _, _, position, _ ->
                    val selectedTitle = researchList[position]
                    val projectId = documents[position].id

                    val intent = Intent(this, ResearchDetailActivity::class.java)
                    intent.putExtra("selectedTitle", selectedTitle)
                    intent.putExtra("projectId", projectId)

                    startActivity(intent)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores de la consulta
                Log.e("ResearchActivity", "Error al obtener datos: $exception")
            }

    }

    private fun showFilterMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.menu_research_options) // Asegúrate de tener este menú definido

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.option_area -> {
                    // Lógica para filtrar por Área de Interés
                    true
                }
                R.id.option_grado -> {
                    // Lógica para filtrar por Grado Académico
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }


}
