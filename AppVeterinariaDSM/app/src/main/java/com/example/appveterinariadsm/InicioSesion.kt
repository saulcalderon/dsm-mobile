package com.example.appveterinariadsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InicioSesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginButton = this.findViewById<Button>(R.id.btnIniciarSesionS)
        val usernameEditText = this.findViewById<EditText>(R.id.etUsuario)
        val passwordEditText = this.findViewById<EditText>(R.id.etPassword)
        loginButton.setOnClickListener{
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username == "Admin" && password == "123456"){
                val builder = AlertDialog.Builder(this)

                builder.setTitle("Mensaje")
                builder.setMessage("Bienvenido Admin")

                builder.setPositiveButton("OK") { dialog, which ->

                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()

                val intent = Intent(this, Pacientes:: class.java)
                startActivity(intent)

            } else{
                val builder = AlertDialog.Builder(this)

                builder.setTitle("Mensaje")
                builder.setMessage("Usuario y/o contraseña incorrectos")

                builder.setPositiveButton("OK") { dialog, which ->

                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        }
    }
}
