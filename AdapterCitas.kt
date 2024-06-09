package com.example.appveterinariadsm.doctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appveterinariadsm.AgregarCitas
import com.example.appveterinariadsm.R

class AdapterCitas{
    interface CitasInteractionListener {


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TextView11: TextView = itemView.findViewById(R.id.textView11)
        val btnNuevaCita: Button = itemView.findViewById(R.id.btnNuevaCita)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = citas.size

}