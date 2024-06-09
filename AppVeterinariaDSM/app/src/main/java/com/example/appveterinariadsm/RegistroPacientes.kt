package com.example.appveterinariadsm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appveterinariadsm.doctor.Doctores
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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
            AuthenticationHelper.logout(this)
            startActivity(intent)
        }

        val buttonRegistroPaciente = this.findViewById<Button>(R.id.btnRegistrarPaciente)
        buttonRegistroPaciente.setOnClickListener {
            registroPaciente()
        }

    }

    fun registroPaciente() {
        val nombrePacienteEditText = findViewById<TextInputEditText>(R.id.tfNombrePaciente)
        val duenoEditText = findViewById<TextInputEditText>(R.id.tfDuenoPaciente)
        val especieEditText = findViewById<TextInputEditText>(R.id.tfEspeciePaciente)


        val nombrePaciente = nombrePacienteEditText.text.toString().trim()
        val dueno = duenoEditText.text.toString().trim()
        val especie = especieEditText.text.toString().trim()



    if (nombrePaciente.isEmpty() || dueno.isEmpty() || especie.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("RegistroPacientes", "Nombre Paciente: $nombrePaciente, Dueño: $dueno, Especie: $especie")
        val database = Firebase.database
        val myRef = database.getReference("pacientes")

        val paciente = Pacientes.Paciente(nombrePaciente, dueno, especie)
        myRef.child(nombrePaciente).setValue(paciente)

        Toast.makeText(this, "Paciente registrado correctamente.", Toast.LENGTH_SHORT).show()

        nombrePacienteEditText.text?.clear()
        duenoEditText.text?.clear()
        especieEditText.text?.clear()


        finish()
    }


}