package com.example.appveterinariadsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AppVeterinariaDSM)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonIniciarSesion = this.findViewById<Button>(R.id.btnIniciarSesion)
        buttonIniciarSesion.setOnClickListener{

            val intent = Intent(this, InicioSesion:: class.java)
            startActivity(intent)
        }

        val buttonRegistrarse = this.findViewById<Button>(R.id.btnRegistrarse)
        buttonRegistrarse.setOnClickListener{

            val intent = Intent(this, Registrarse:: class.java)
            startActivity(intent)
        }
    }
}

