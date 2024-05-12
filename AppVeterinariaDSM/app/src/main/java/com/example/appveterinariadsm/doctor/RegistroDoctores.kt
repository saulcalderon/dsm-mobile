package com.example.appveterinariadsm.doctor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appveterinariadsm.MainActivity
import com.example.appveterinariadsm.Pacientes
import com.example.appveterinariadsm.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.database.database


class RegistroDoctores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_doctores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //  Start - Menu

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

        // End - Menu

        val buttonRegistroDoctor = this.findViewById<Button>(R.id.btnRegistrarDoctor)
        buttonRegistroDoctor.setOnClickListener {
            registroDoctor()
        }
    }

    fun registroDoctor() {
        val nombreEditText = findViewById<TextInputEditText>(R.id.rd_campo_nombre)
        val especialidadEditText = findViewById<TextInputEditText>(R.id.rd_campo_especialidad)
        val usuarioEditText = findViewById<TextInputEditText>(R.id.rd_campo_usuario)
        val passwordEditText = findViewById<TextInputEditText>(R.id.etPasswordRegistroDoctor)

        val nombre = nombreEditText.text.toString().trim()
        val especialidad = especialidadEditText.text.toString().trim()
        val usuario = usuarioEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()


        if (nombre.isEmpty() || especialidad.isEmpty() || usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("RegistroDoctores", "Nombre: $nombre, Especialidad: $especialidad, Usuario: $usuario, Password: $password")

        val database = Firebase.database
        val myRef = database.getReference("doctores")

        val doctor = Doctores.Doctor(nombre, especialidad, usuario, password)
        myRef.child(usuario).setValue(doctor)

        Toast.makeText(this, "Doctor registrado correctamente.", Toast.LENGTH_SHORT).show()

        nombreEditText.text?.clear()
        especialidadEditText.text?.clear()
        usuarioEditText.text?.clear()
        passwordEditText.text?.clear()

        finish()
    }
}

