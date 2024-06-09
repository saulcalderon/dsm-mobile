package com.example.appveterinariadsm

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistroCitas : AppCompatActivity()  {

    private val calendar = Calendar.getInstance()
    private var date: String? = null
    private var time: String? = null
    private lateinit var mPickTimeBtn: Button
    private lateinit var btnDatePicker: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_citas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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

        mPickTimeBtn = findViewById<Button>(R.id.pickTimeBtn)

        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                mPickTimeBtn.text = "Hora: ${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}"
                time = "${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}"
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        btnDatePicker = findViewById<Button>(R.id.btnDatePicker)

        btnDatePicker.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                btnDatePicker.text = "Fecha: $formattedDate"
                date = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
         datePickerDialog.show()
         }

        val buttonRegistroCita = this.findViewById<Button>(R.id.btnRegistrarCita)
        buttonRegistroCita.setOnClickListener {
            registroCita()
        }

    }

    private fun registroCita() {
        val nombrePacienteEditText = findViewById<TextInputEditText>(R.id.citaNombrePaciente)

        val nombrePaciente = nombrePacienteEditText.text.toString().trim()

        if (nombrePaciente.isEmpty() || date == null || time == null) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("RegistroCitas", "Nombre Paciente: $nombrePaciente, Fecha: $date, Hora: $time")
        val database = Firebase.database
        val myRef = database.getReference("citas")

        val cita = Pacientes.Cita("$date $time", nombrePaciente)
        myRef.child(nombrePaciente).setValue(cita)
        
        Toast.makeText(this, "Cita registrada correctamente.", Toast.LENGTH_SHORT).show()

        nombrePacienteEditText.text?.clear()
        btnDatePicker.text = "Fecha: "
        mPickTimeBtn.text = "Hora: "
        date = null
        time = null

        finish()


    }
    
}