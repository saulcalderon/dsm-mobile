package com.example.appveterinariadsm.doctor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appveterinariadsm.MainActivity
import com.example.appveterinariadsm.Pacientes
import com.example.appveterinariadsm.R
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Doctores : AppCompatActivity() {

    data class Doctor(val nombre: String? = null, val especialidad: String? = null, val usuario: String? = null, val password: String? = null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Start - Menu
        val buttonND = this.findViewById<Button>(R.id.btnNuevoPaciente)
        buttonND.setOnClickListener{

            val intent = Intent(this, RegistroDoctores:: class.java)
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
        // End - Menu

        val recyclerView = this.findViewById<RecyclerView>(R.id.rvDoctores)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchDoctorsFromFirebase(recyclerView)

    }
    private fun fetchDoctorsFromFirebase(recyclerView: RecyclerView) {
       val doctorsList = mutableListOf<Doctor>()
       val database = Firebase.database
       val myRef = database.getReference("doctores")

       myRef.addValueEventListener(object : ValueEventListener {
           override fun onDataChange(snapshot: DataSnapshot) {
               doctorsList.clear()
               for (postSnapshot in snapshot.children) {
                   val doctor = postSnapshot.getValue(Doctor::class.java)
                   doctor?.let {
                       doctorsList.add(it)
                       Log.d("Doctores", "Doctor fetched: ${it.nombre}, ${it.especialidad}, ${it.usuario}")
                   }
               }
               val adapter = DoctorAdapter(doctorsList)
               recyclerView.adapter = adapter
           }

           override fun onCancelled(databaseError: DatabaseError) {
               Log.e("Doctores", "Failed to read doctor values.", databaseError.toException())
           }
       })
   }
}