package com.will.bananap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.will.bananap.ui.users.AddResearchActivity
import com.will.bananap.ui.users.LoginActivity
import com.will.bananap.ui.users.ResearchActivity

const val valorIntentLogin = 1

class MainActivity : AppCompatActivity() {

    private var auth = FirebaseAuth.getInstance()
    private var email: String? = null
    private var contra: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Intenta obtener el token del usuario del local storage, sino llama a la ventana de registro
        val prefe = getSharedPreferences("appData", Context.MODE_PRIVATE)
        email = prefe.getString("email", "")
        contra = prefe.getString("contra", "")

        if (email.toString().trim { it <= ' ' }.isEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, valorIntentLogin)
        }

        val fabOptions: FloatingActionButton = findViewById(R.id.fabOptions)
        fabOptions.setOnClickListener { view ->
            showOptionsMenu(view)
        }
    }

    private fun showOptionsMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.options_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_option1 -> {
                    startActivity(Intent(this, ResearchActivity::class.java))
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }
}
