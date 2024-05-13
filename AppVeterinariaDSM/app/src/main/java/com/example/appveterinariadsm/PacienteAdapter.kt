package com.example.appveterinariadsm
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appveterinariadsm.R
class PacienteAdapter(private val pacientes: List<Pacientes.Paciente>, private val listener: PacienteInteractionListener) :
    RecyclerView.Adapter<PacienteAdapter.ViewHolder>() {

    interface PacienteInteractionListener {
        fun onDeletePaciente(paciente: Pacientes.Paciente)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pacienteTextView: TextView = itemView.findViewById(R.id.view_paciente_nombrePaciente)
        val btnBorrarPaciente: Button = itemView.findViewById(R.id.btnBorrarPaciente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.paciente_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPaciente = pacientes[position]
        holder.pacienteTextView.text = currentPaciente.nombrePaciente
        holder.btnBorrarPaciente.setOnClickListener{ listener.onDeletePaciente(currentPaciente) }
    }

    override fun getItemCount(): Int = pacientes.size
}