package com.example.proyecto_assassinscreed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.Personaje
import com.example.proyecto_assassinscreed.PersonajeProvider.Companion.personajeList
import com.example.proyecto_assassinscreed.R

class PersonajeAdapter(private val personajeList: List<Personaje>): RecyclerView.Adapter<PersonajeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PersonajeViewHolder(layoutInflater.inflate(R.layout.item_personaje,parent,false))
    }

    override fun getItemCount(): Int {
        return personajeList.size
    }

    override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int) {
        val item = personajeList[position]
        holder.render(item)
    }
}