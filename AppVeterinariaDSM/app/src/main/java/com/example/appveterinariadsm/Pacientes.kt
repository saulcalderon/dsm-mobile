package com.example.appveterinariadsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.AppCompatImageButton

class Pacientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pacientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar la base de datos Firebase
        database = FirebaseDatabase.getInstance().reference.child("pacientes")
        pacientesList = mutableListOf()

        // Configurar el botón "Guardar"
        btnGuardar.setOnClickListener {
            guardarPacienteEnFirebase(
                etNombre.text.toString(),
                etEspecie.text.toString(),
                etRaza.text.toString(),
                etEdad.text.toString()
            )
            limpiarCampos()
        }

        // Configurar el botón "Ver"
        btnVer.setOnClickListener {
            mostrarPacientes()
        }

        // Escuchar cambios en la base de datos Firebase
        database.addValueEventListener(object : ValueEventListener {
            fun onDataChange(snapshot: DataSnapshot) {
                pacientesList.clear()
                for (data in snapshot.children) {
                    val paciente = data.getValue(Paciente::class.java)
                    paciente?.let { pacientesList.add(it) }
                }
            }
        }

            val buttonNP = this.findViewById<Button>(R.id.btnNuevoPaciente)
        buttonNP.setOnClickListener{

            val intent = Intent(this, RegistroPacientes:: class.java)
            startActivity(intent)
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
    private fun guardarPacienteEnFirebase(nombre: String, especie: String, raza: String, edad: String) {
        val pacienteId = database.push().key
        val paciente = Paciente(nombre, especie, raza, edad)
        pacienteId?.let {
            database.child(it).setValue(paciente)
        }
    }
    private fun limpiarCampos() {
        etNombre.text.clear()
        etEspecie.text.clear()
        etRaza.text.clear()
        etEdad.text.clear()
    }
    private fun mostrarPacientes() {
        val stringBuilder = StringBuilder()
        for (paciente in pacientesList) {
            stringBuilder.append("Nombre: ${paciente.nombre}\n")
            stringBuilder.append("Especie: ${paciente.especie}\n")
            stringBuilder.append("Raza: ${paciente.raza}\n")
            stringBuilder.append("Edad: ${paciente.edad}\n\n")
        }
        tvInformacionPaciente.text = stringBuilder.toString()
    }
}
data class Paciente(
    val nombre: String = "",
    val especie: String = "",
    val raza: String = "",
    val edad: String = ""
)