package com.example.appveterinariadsm

import android.content.Intent
import android.graphics.Color
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
import com.example.appveterinariadsm.cita.CitaAdapter
import com.example.appveterinariadsm.doctor.Doctores
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Pacientes : AppCompatActivity() {

    data class Paciente(
        val nombrePaciente: String? = null,
        val dueno: String? = null,
        val especie: String? = null
    )

    data class Cita(val fecha: String? = null, val hora: String? = null, val nombrePaciente: String? = null)

    private var seleccionado = "Pacientes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pacientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonNP = this.findViewById<Button>(R.id.btnNuevoPaciente)

        if (seleccionado == "Pacientes") {
            buttonNP.setText("Registrar nuevo paciente")
        } else {
            buttonNP.setText("Registrar nueva cita")
        }

        buttonNP.setOnClickListener {

            if (seleccionado == "Pacientes") {
                val intent = Intent(this, RegistroPacientes::class.java)
                startActivity(intent)
            } else {
                // val intent = Intent(this, RegistroCitas::class.java)
                // startActivity(intent)
            }
        }

        val buttonPacientes = this.findViewById<Button>(R.id.mPacientes)
        buttonPacientes.setOnClickListener {

            val intent = Intent(this, Pacientes::class.java)
            startActivity(intent)
        }

        val buttonDoctores = this.findViewById<Button>(R.id.mDoctores)
        buttonDoctores.setOnClickListener {

            val intent = Intent(this, Doctores::class.java)
            startActivity(intent)
        }

        val buttonLogout = this.findViewById<Button>(R.id.mLogout)
        buttonLogout.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            AuthenticationHelper.logout(this)
            startActivity(intent)
        }
        val recyclerView = this.findViewById<RecyclerView>(R.id.rvPacientes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchPacientesFromFirebase(recyclerView)

        val btnPacientes = findViewById<Button>(R.id.btnPacientes);
        val btnCitas = findViewById<Button>(R.id.btnCitas);

        btnPacientes.setBackgroundColor(Color.parseColor("#353EE1"));
        btnCitas.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btnCitas.setTextColor(Color.parseColor("#353EE1"));

        btnPacientes.setOnClickListener {
            btnPacientes.setBackgroundColor(Color.parseColor("#353EE1"));
            btnPacientes.setTextColor(Color.parseColor("#FFFFFF"));
            btnCitas.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnCitas.setTextColor(Color.parseColor("#353EE1"));
            fetchPacientesFromFirebase(recyclerView)
            seleccionado = "Pacientes"
            buttonNP.setText("Registrar nuevo paciente")

        }

        btnCitas.setOnClickListener {
            btnCitas.setBackgroundColor(Color.parseColor("#353EE1"));
            btnCitas.setTextColor(Color.parseColor("#FFFFFF"));
            btnPacientes.setBackgroundColor(Color.parseColor("#FFFFFF"));
            btnPacientes.setTextColor(Color.parseColor("#353EE1"));
            fetchCitasFromFirebase(recyclerView)
            seleccionado = "Citas"
            buttonNP.setText("Registrar nueva cita")
        }

    }



    private fun fetchPacientesFromFirebase(recyclerView: RecyclerView) {
        val pacientesList = mutableListOf<Paciente>()
        val database = Firebase.database
        val myRef = database.getReference("pacientes")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pacientesList.clear()
                for (postSnapshot in snapshot.children) {
                    val paciente = postSnapshot.getValue(Paciente::class.java)
                    paciente?.let {
                        pacientesList.add(it)
                        Log.d("Pacientes", "Paciente fetched: ${it.nombrePaciente}, ${it.dueno}, ${it.especie}")
                    }
                }
                val adapter = PacienteAdapter(pacientesList, object : PacienteAdapter.PacienteInteractionListener {
                    override fun onDeletePaciente(paciente: Paciente) {

                        Log.d("Paciente", "Delete paciente: ${paciente.nombrePaciente}")
                        myRef.child(paciente.nombrePaciente!!).removeValue()
                        Toast.makeText(this@Pacientes, "Paciente eliminado correctamente", Toast.LENGTH_SHORT).show()
                    }
                })
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Pacientes", "Error al leer los pacientes.", databaseError.toException())
            }
        })
    }

    private fun fetchCitasFromFirebase(recyclerView: RecyclerView) {
        val citasList = mutableListOf<Cita>()
        val database = Firebase.database
        val myRef = database.getReference("citas")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                citasList.clear()
                for (postSnapshot in snapshot.children) {
                    val cita = postSnapshot.getValue(Cita::class.java)
                    cita?.let {
                        citasList.add(it)
                        Log.d("Citas", "Cita fetched: ${it.fecha}, ${it.hora}")
                    }
                }
                val adapter = CitaAdapter(citasList, object : CitaAdapter.CitaInteractionListener {
                    override fun onDeleteCita(cita: Cita) {

                       Log.d("Cita", "Delete cita: ${cita}")
                       myRef.child(cita.nombrePaciente!!).removeValue()
                       Toast.makeText(this@Pacientes, "Cita eliminada correctamente", Toast.LENGTH_SHORT).show()
                    }
                })
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Citas", "Error al leer las citas.", databaseError.toException())
            }
        })
    }
}
