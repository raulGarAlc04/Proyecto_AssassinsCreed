package com.example.proyecto_assassinscreed

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isNotEmpty
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityUpdateCiudadBinding
import com.example.proyecto_assassinscreed.databinding.ActivityUpdateLiderBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateCiudad : AppCompatActivity() {
    lateinit var binding: ActivityUpdateCiudadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCiudadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch {
            val nombresPersonajes = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            val nombresCiudades = MiPersonajesApp.database.ciudadesDao().nombresCiudades()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterPersonajes = ArrayAdapter(this@UpdateCiudad, R.layout.simple_spinner_item, nombresPersonajes)
                val adapterCiudades = ArrayAdapter(this@UpdateCiudad, R.layout.simple_spinner_item, nombresCiudades)
                adapterPersonajes.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                adapterCiudades.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.spNuevoGobernador.adapter = adapterPersonajes
                binding.spCiudad.adapter = adapterCiudades
            }
        }

        binding.bActualizarCiudad.setOnClickListener {
            if (binding.spCiudad.isNotEmpty() && binding.spNuevoGobernador.isNotEmpty()) {
                val nuevoGobernador = binding.spNuevoGobernador.selectedItem.toString()
                updateCiudad(nuevoGobernador)

                val intent = Intent(this, ListarCiudades::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
            }

        }

        binding.bSalirSinGuardar.setOnClickListener {
            val intent = Intent(this, ListarCiudades::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

    }

    fun updateCiudad(nuevoGobernador: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val ciudades = MiPersonajesApp.database.ciudadesDao().ciudadPorNombre(binding.spCiudad.selectedItem.toString())

            if (ciudades.isNotEmpty()) {
                val ciudad = ciudades[0]
                ciudad.gobernador = nuevoGobernador
                MiPersonajesApp.database.ciudadesDao().updateCiudad(ciudad)

                runOnUiThread {
                    //clearTextos()
                    Toast.makeText(this@UpdateCiudad, "Ciudad actualizada correctamente", Toast.LENGTH_SHORT).show()
                    //actualizarRecyclerView()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@UpdateCiudad, "Este personaje no existe en la base de datos", Toast.LENGTH_SHORT).show()
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