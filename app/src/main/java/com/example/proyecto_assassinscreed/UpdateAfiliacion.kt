package com.example.proyecto_assassinscreed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityUpdateAfiliacionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateAfiliacion : ActivityWithMenus() {
    lateinit var binding: ActivityUpdateAfiliacionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_update_afiliacion)
        binding = ActivityUpdateAfiliacionBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.bActualizar.setOnClickListener {
            val nuevaAfiliacion = binding.nuevaAfiliacion.text.toString()

            updatePersonaje(nuevaAfiliacion)
        }

    }

    fun updatePersonaje(nuevaAfiliacion: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val personajes = MiPersonajesApp.database.personajeDao().personajePorNombre(binding.nombrePersonajeActualizar.text.toString())

            if (personajes.isNotEmpty()) {
                val personaje = personajes[0]
                personaje.afiliacion = nuevaAfiliacion

                MiPersonajesApp.database.personajeDao().updatePersonaje(personaje)
            }

        }
    }
}