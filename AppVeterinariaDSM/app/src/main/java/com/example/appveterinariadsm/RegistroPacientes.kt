package com.example.appveterinariadsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data class Patient(
    val name: String,
    val breed: String,
    val species: String

)

class RegistroPacientes : AppCompatActivity() {

    //  private val database = FirebaseDatabase.getInstance()
    // private val pacientesRef = database.getReference("pacientes")

    private val patientList = mutableListOf<Patient>()

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
        //Registro en base de datos
        //val btnRegistrarPaciente = this.findViewById<Button>(R.id.btnRegistrarPaciente)
        //btnRegistrarPaciente.setOnClickListener {
        //     guardarPaciente()
        //}

        //Registro Local
        val btnRegristrarPaciente = this.findViewById<Button>(R.id.btnRegistrarPaciente)
        btnRegristrarPaciente.setOnClickListener{
            val tfNombrePaciente = this.findViewById<EditText>(R.id.tfNombrePaciente)
            val tfDuenoPaciente = this.findViewById<EditText>(R.id.tfDuenoPaciente)
            val tfEspeciePaciente = this.findViewById<EditText>(R.id.tfEspeciePaciente)

            val name = tfNombrePaciente.text.toString()
            val breef = tfDuenoPaciente.text.toString()
            val species = tfEspeciePaciente.text.toString()

            val newPatient = Patient(name, breef, species)
            patientList.add(newPatient)

            tfNombrePaciente.text.clear()
            tfDuenoPaciente.text.clear()
            tfEspeciePaciente.text.clear()
        }

        //private fun guardarPaciente(){
        //    val tfNombrePacientes = this.findViewById<EditText>(R.id.tfNombrePaciente)
        //    val  tfDuenoPacientes = this.findViewById<EditText>(R.id.tfDuenoPaciente)
        //    val tfEspeciePacientes = this.findViewById<EditText>(R.id.tfEspeciePaciente)

        //    val nombre = tfNombrePacientes.text.toString().trim()
        //    val dueno = tfDuenoPacientes.text.toString().trim()
        //   val especie = tfEspeciePacientes.text.toString().trim()

        //    if (nombre.isNotEmpty() && dueno.isNotEmpty() && especie.isNotEmpty()) {
        //        val pacienteId = pacientesRef.push().key
        //        val paciente = Paciente(nombre, dueno, especie)

        //        if (pacienteId != null) {
        //            pacientesRef.child(pacienteId).setValue(paciente)
        //            Toast.makeText(this, "Paciente guardado correctamente", Toast.LENGTH_SHORT).show()
        //            limpiarCampos()
        //       } else {
        //            Toast.makeText(this, "Error al generar ID del paciente", Toast.LENGTH_SHORT).show()
        //        }
        //    } else {
        //        Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
        //    }
// }
        //private fun limpiarCampos(){
        //    val tfNombrePacientes = this.findViewById<EditText>(R.id.tfNombrePaciente)
        //    val  tfDuenoPacientes = this.findViewById<EditText>(R.id.tfDuenoPaciente)
        //     val tfEspeciePacientes = this.findViewById<EditText>(R.id.tfEspeciePaciente)
        //     tfNombrePacientes.text.clear()
        //     tfDuenoPacientes.text.clear()
        //    tfEspeciePacientes.text.clear()
        //}
    }
}