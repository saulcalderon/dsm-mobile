package com.example.appveterinariadsm.doctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appveterinariadsm.R

class DoctorAdapter(private val doctores: List<Doctores.Doctor>, private val listener: DoctorInteractionListener) :
        RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    interface DoctorInteractionListener {
        fun onDeleteDoctor(doctor: Doctores.Doctor)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorTextView: TextView = itemView.findViewById(R.id.view_doctor_nombre)
        val btnBorrarDoctor: Button = itemView.findViewById(R.id.btnBorrarDoctor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.doctor_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoctor = doctores[position]
        holder.doctorTextView.text = currentDoctor.nombre
        holder.btnBorrarDoctor.setOnClickListener{ listener.onDeleteDoctor(currentDoctor) }
    }

    override fun getItemCount(): Int = doctores.size
}