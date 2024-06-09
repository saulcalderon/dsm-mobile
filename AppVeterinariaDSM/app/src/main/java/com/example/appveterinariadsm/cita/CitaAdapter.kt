package com.example.appveterinariadsm.cita

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appveterinariadsm.Pacientes
import com.example.appveterinariadsm.R

class CitaAdapter(private val citas: List<Pacientes.Cita>, private val listener: CitaInteractionListener) :
        RecyclerView.Adapter<CitaAdapter.ViewHolder>() {

    interface CitaInteractionListener {
        fun onDeleteCita(cita: Pacientes.Cita)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val citaTextView: TextView = itemView.findViewById(R.id.view_paciente_nombrePaciente)
        val fechaTextView: TextView = itemView.findViewById(R.id.fecha)
        // val btnBorrarCita: Button = itemView.findViewById(R.id.btnBorrarCita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.paciente_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCita = citas[position]
        holder.citaTextView.text = currentCita.nombrePaciente
        holder.fechaTextView.text = currentCita.fechaHora
        // holder.btnBorrarCita.setOnClickListener{ listener.onDeleteCita(currentCita) }
    }

    override fun getItemCount(): Int = citas.size
}