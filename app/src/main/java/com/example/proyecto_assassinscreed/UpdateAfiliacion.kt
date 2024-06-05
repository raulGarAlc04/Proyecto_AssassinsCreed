package com.example.proyecto_assassinscreed

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isNotEmpty
import com.example.proyecto_assassinscreed.adapter.PersonajeAdapter
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityUpdateAfiliacionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateAfiliacion : AppCompatActivity() {
    lateinit var binding: ActivityUpdateAfiliacionBinding
    //lateinit var adapter: PersonajeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_update_afiliacion)
        binding = ActivityUpdateAfiliacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //adapter = PersonajeAdapter(mutableListOf())

        CoroutineScope(Dispatchers.IO).launch {
            val nombresPersonajes = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            val nombresAfiliaciones = MiPersonajesApp.database.afiliacionesDao().nombresAfiliaciones()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterPersonajes = ArrayAdapter(this@UpdateAfiliacion, com.example.proyecto_assassinscreed.R.layout.item_spinner, nombresPersonajes)
                val adapterAfiliaciones = ArrayAdapter(this@UpdateAfiliacion, com.example.proyecto_assassinscreed.R.layout.item_spinner, nombresAfiliaciones)
                adapterPersonajes.setDropDownViewResource(com.example.proyecto_assassinscreed.R.layout.item_spinner)
                adapterAfiliaciones.setDropDownViewResource(com.example.proyecto_assassinscreed.R.layout.item_spinner)
                binding.nombrePersonajeActualizar.adapter = adapterPersonajes
                binding.nuevaAfiliacion.adapter = adapterAfiliaciones
            }
        }

        binding.bActualizarAfiliacion.setOnClickListener {
            if (binding.nombrePersonajeActualizar.isNotEmpty() && binding.nuevaAfiliacion.isNotEmpty()) {
                val nuevaAfiliacion = binding.nuevaAfiliacion.selectedItem.toString()
                updatePersonaje(nuevaAfiliacion)

                val intent = Intent(this, ListarPersonajes::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
            }

        }

        binding.bSalirSinGuardar.setOnClickListener {
            val intent = Intent(this, ListarPersonajes::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

    }

    fun updatePersonaje(nuevaAfiliacion: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val personajes = MiPersonajesApp.database.personajeDao().personajePorNombre(binding.nombrePersonajeActualizar.selectedItem.toString())

            if (personajes.isNotEmpty()) {
                val personaje = personajes[0]
                personaje.afiliacion = nuevaAfiliacion
                MiPersonajesApp.database.personajeDao().updatePersonaje(personaje)

                runOnUiThread {
                    //clearTextos()
                    Toast.makeText(this@UpdateAfiliacion, "Personaje actualizado correctamente", Toast.LENGTH_SHORT).show()
                    //actualizarRecyclerView()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@UpdateAfiliacion, "Este personaje no existe en la base de datos", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


    /*fun actualizarRecyclerView() {
        CoroutineScope(Dispatchers.IO).launch {
            val personajes = MiPersonajesApp.database.personajeDao().getAllPersonajes()
            runOnUiThread {
                adapter.actualizarPersonajes(personajes)
            }
        }
    }*/
}