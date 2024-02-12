package com.example.proyecto_assassinscreed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityUpdateAfiliacionBinding

class UpdateAfiliacion : ActivityWithMenus() {
    lateinit var binding: ActivityUpdateAfiliacionBinding
    lateinit var personajes: MutableList<Personajes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_update_afiliacion)
        binding = ActivityUpdateAfiliacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bComprobar.setOnClickListener {
            personajes = MiPersonajesApp.database.personajeDao().personajePorNombre(binding.nombrePersonajeActualizar.text.toString())

            if (personajes.isNotEmpty()) {
                binding.nuevaAfiliacion.isEnabled = true
                binding.bActualizar.isEnabled = true
            }
        }

        binding.bActualizar.setOnClickListener {
            val nuevaAfiliacion = binding.nuevaAfiliacion.text.toString()

            updatePersonaje(nuevaAfiliacion)
        }

    }

    fun updatePersonaje(nuevaAfiliacion: String) {
        val personaje = personajes[0]
        personaje.afiliacion = nuevaAfiliacion

        MiPersonajesApp.database.personajeDao().updatePersonaje(personaje)
    }
}