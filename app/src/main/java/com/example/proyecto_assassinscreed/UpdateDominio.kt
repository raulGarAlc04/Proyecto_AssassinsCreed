package com.example.proyecto_assassinscreed

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isNotEmpty
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityUpdateDominioBinding
import com.example.proyecto_assassinscreed.databinding.ActivityUpdateLiderBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateDominio : AppCompatActivity() {
    lateinit var binding: ActivityUpdateDominioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDominioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch {
            val nombresCiudades = MiPersonajesApp.database.ciudadesDao().nombresCiudades()
            val nombresDominios = MiPersonajesApp.database.dominioDao().nombresDiminios()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterCiudades = ArrayAdapter(this@UpdateDominio, com.example.proyecto_assassinscreed.R.layout.item_spinner, nombresCiudades)
                val adapterDominios = ArrayAdapter(this@UpdateDominio, com.example.proyecto_assassinscreed.R.layout.item_spinner, nombresDominios)
                adapterCiudades.setDropDownViewResource(com.example.proyecto_assassinscreed.R.layout.item_spinner)
                adapterDominios.setDropDownViewResource(com.example.proyecto_assassinscreed.R.layout.item_spinner)
                binding.spNuevaCapital.adapter = adapterCiudades
                binding.spDominacion.adapter = adapterDominios
            }
        }

        binding.bActualizarDominio.setOnClickListener {
            if (binding.spDominacion.isNotEmpty() && binding.spNuevaCapital.isNotEmpty()) {
                val nuevaCapital = binding.spNuevaCapital.selectedItem.toString()
                updateDominio(nuevaCapital)

                val intent = Intent(this, ListarDominios::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
            }

        }

        binding.bSalirSinGuardar.setOnClickListener {
            val intent = Intent(this, ListarDominios::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

    }

    fun updateDominio(nuevaCapital: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val dominios = MiPersonajesApp.database.dominioDao().dominioPorNombre(binding.spDominacion.selectedItem.toString())

            if (dominios.isNotEmpty()) {
                val dominio = dominios[0]
                dominio.capital = nuevaCapital
                MiPersonajesApp.database.dominioDao().updateDominio(dominio)

                runOnUiThread {
                    //clearTextos()
                    Toast.makeText(this@UpdateDominio, "Personaje actualizado correctamente", Toast.LENGTH_SHORT).show()
                    //actualizarRecyclerView()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@UpdateDominio, "Este personaje no existe en la base de datos", Toast.LENGTH_SHORT).show()
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