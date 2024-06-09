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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appveterinariadsm.doctor.AdapterCitas
import com.example.appveterinariadsm.doctor.Doctores
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class AgregarCitas : AppCompatActivity() {

    data class Cita(
        val ID_Doctor: String? = null,
        val ID_Paciente: String? = null,
        val Fecha: String? = null,
        val Hora: String? = null
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.Agregarcitas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//Boton para agregar pacientes en otra pestana
        val buttonNP = this.findViewById<Button>(R.id.mPacientes)
        buttonNP.setOnClickListener {

            val intent = Intent(this, RegistroPacientes::class.java)
            startActivity(intent)
        }

//Button para ir a agregar doctores
        val buttonDoctores = this.findViewById<Button>(R.id.mDoctores)
        buttonDoctores.setOnClickListener {

            val intent = Intent(this, Doctores::class.java)
            startActivity(intent)
        }
//Button para salir de la pantalla
        val buttonLogout = this.findViewById<Button>(R.id.mLogout)
        buttonLogout.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
//programar agregar
        val btncita = this.findViewById<Button>(R.id.btnNuevaCita)
        btncita.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
//Conexion a la base de datos
private fun fetchCitasFromFirebase(recyclerView: RecyclerView) {
    val CitasList = mutableListOf<AgregarCitas.Cita>()
    val database = Firebase.database
    val myRef = database.getReference("citas")

    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            CitasList.clear()
            for (postSnapshot in snapshot.children) {
                val cita = postSnapshot.getValue(AgregarCitas.Cita::class.java)
                cita?.let {
                    CitasList.add(it)
                    Log.d("AgregarCitas", "Citas fetched: ${it.ID_Doctor}, ${it.ID_Paciente}, ${it.Fecha}, ${it.Hora}")
                }
            }
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.e("Citas", "Failed to read doctor values.", databaseError.toException())
        }
    })
}
}
