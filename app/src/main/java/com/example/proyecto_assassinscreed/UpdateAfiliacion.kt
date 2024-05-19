package com.example.proyecto_assassinscreed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_assassinscreed.adapter.PersonajeAdapter
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityUpdateAfiliacionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateAfiliacion : ActivityMenuPersonajes() {
    lateinit var binding: ActivityUpdateAfiliacionBinding
    lateinit var adapter: PersonajeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_update_afiliacion)
        binding = ActivityUpdateAfiliacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PersonajeAdapter(mutableListOf())

        binding.bActualizarAfiliacion.setOnClickListener {


            if (binding.nombrePersonajeActualizar.text.isNotEmpty() && binding.nuevaAfiliacion.text.isNotEmpty()) {
                val nuevaAfiliacion = binding.nuevaAfiliacion.text.toString()
                updatePersonaje(nuevaAfiliacion)
            } else {
                Toast.makeText(this, "El campo no puede estar vacio", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun updatePersonaje(nuevaAfiliacion: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val personajes = MiPersonajesApp.database.personajeDao().personajePorNombre(binding.nombrePersonajeActualizar.text.toString())

            if (personajes.isNotEmpty()) {
                val personaje = personajes[0]
                personaje.afiliacion = nuevaAfiliacion
                MiPersonajesApp.database.personajeDao().updatePersonaje(personaje)

                runOnUiThread {
                    clearTextos()
                    Toast.makeText(this@UpdateAfiliacion, "Personaje actualizado correctamente", Toast.LENGTH_SHORT).show()
                    actualizarRecyclerView()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@UpdateAfiliacion, "Este personaje no existe en la base de datos", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    fun clearTextos() {
        binding.nombrePersonajeActualizar.setText("")
        binding.nuevaAfiliacion.setText("")
    }

    fun actualizarRecyclerView() {
        CoroutineScope(Dispatchers.IO).launch {
            val personajes = MiPersonajesApp.database.personajeDao().getAllPersonajes()
            runOnUiThread {
                adapter.actualizarPersonajes(personajes)
            }
        }
    }
}