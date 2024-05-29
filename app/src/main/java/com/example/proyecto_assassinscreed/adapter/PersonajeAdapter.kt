package com.example.proyecto_assassinscreed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.R
import com.example.proyecto_assassinscreed.database.Personajes

class PersonajeAdapter(var personajeList: MutableList<Personajes>, val deletePersonajeLista: (Personajes) -> Unit): RecyclerView.Adapter<PersonajeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PersonajeViewHolder(layoutInflater.inflate(R.layout.item_personaje,parent,false))
    }

    override fun getItemCount(): Int {
        return personajeList.size
    }

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) {
        val item = personajeList[position]
        holder.bind(item, deletePersonajeLista)
    }

    fun actualizarPersonajes(personajes: MutableList<Personajes>) {
        this.personajeList = personajes
        notifyDataSetChanged()
    }
}