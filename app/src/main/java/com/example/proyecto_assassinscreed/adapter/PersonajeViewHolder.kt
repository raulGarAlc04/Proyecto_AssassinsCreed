package com.example.proyecto_assassinscreed.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto_assassinscreed.Personaje
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ItemPersonajeBinding

class PersonajeViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val binding = ItemPersonajeBinding.bind(view)

    fun render(personajeModel: Personajes) {
        binding.personajeName.text = personajeModel.nombrePersonaje
        binding.anioMuerte.text = personajeModel.anioFallecimiento
        binding.lugarMuerte.text = personajeModel.lugarFallecimiento
        binding.afiliado.text = personajeModel.afiliacion
        binding.villano.text = personajeModel.villano.toString()

    }
}