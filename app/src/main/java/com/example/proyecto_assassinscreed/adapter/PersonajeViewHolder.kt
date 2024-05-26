package com.example.proyecto_assassinscreed.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ItemPersonajeBinding

class PersonajeViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val binding = ItemPersonajeBinding.bind(view)

    fun render(personajeModel: Personajes) {
        binding.personajeName.text = personajeModel.nombrePersonaje
        binding.anioMuerte.text = personajeModel.anioFallecimiento
        binding.lugarMuerte.text = personajeModel.lugarFallecimiento
        binding.afiliado.text = personajeModel.afiliacion
        if (personajeModel.villano) {
            binding.villano.text = "Villano"
        } else {
            binding.villano.text = "Bueno"
        }
    }

    fun bind(personaje: Personajes) {
        binding.personajeName.text = personaje.nombrePersonaje
        binding.anioMuerte.text = personaje.anioFallecimiento
        binding.lugarMuerte.text = personaje.lugarFallecimiento
        binding.afiliado.text = personaje.afiliacion
        if (personaje.villano) {
            binding.villano.text = "Villano"
        } else {
            binding.villano.text = "Bueno"
        }
    }
}