package com.example.appveterinariadsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appveterinariadsm.doctor.Doctores

class RegistroPacientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_pacientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonPacientes = this.findViewById<Button>(R.id.mPacientes)
        buttonPacientes.setOnClickListener{

            val intent = Intent(this, Pacientes:: class.java)
            startActivity(intent)
        }

        val buttonDoctores = this.findViewById<Button>(R.id.mDoctores)
        buttonDoctores.setOnClickListener{

            val intent = Intent(this, Doctores:: class.java)
            startActivity(intent)
        }

        val buttonLogout = this.findViewById<Button>(R.id.mLogout)
        buttonLogout.setOnClickListener{

            val intent = Intent(this, MainActivity:: class.java)
            startActivity(intent)
        }
    }
}