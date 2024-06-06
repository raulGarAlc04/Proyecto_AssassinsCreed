package com.example.proyecto_assassinscreed

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isNotEmpty
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityUpdateLiderBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateLider : AppCompatActivity() {
    lateinit var binding: ActivityUpdateLiderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateLiderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch {
            val nombresPersonajes = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            val nombresAfiliaciones = MiPersonajesApp.database.afiliacionesDao().nombresAfiliaciones()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterPersonajes = ArrayAdapter(this@UpdateLider, com.example.proyecto_assassinscreed.R.layout.item_spinner, nombresPersonajes)
                val adapterAfiliaciones = ArrayAdapter(this@UpdateLider, com.example.proyecto_assassinscreed.R.layout.item_spinner, nombresAfiliaciones)
                adapterPersonajes.setDropDownViewResource(com.example.proyecto_assassinscreed.R.layout.item_spinner)
                adapterAfiliaciones.setDropDownViewResource(com.example.proyecto_assassinscreed.R.layout.item_spinner)
                binding.spNuevoLider.adapter = adapterPersonajes
                binding.spAfiliacion.adapter = adapterAfiliaciones
            }
        }

        binding.bActualizarLider.setOnClickListener {
            if (binding.spAfiliacion.isNotEmpty() && binding.spNuevoLider.isNotEmpty()) {
                val nuevoLider = binding.spNuevoLider.selectedItem.toString()
                updateAfiliacion(nuevoLider)

                val intent = Intent(this, ListarAfiliaciones::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show()
            }

        }

        binding.bSalirSinGuardar.setOnClickListener {
            val intent = Intent(this, ListarAfiliaciones::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

    }

    fun updateAfiliacion(nuevaAfiliacion: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val afiliaciones = MiPersonajesApp.database.afiliacionesDao().afiliacionPorNombre(binding.spAfiliacion.selectedItem.toString())

            if (afiliaciones.isNotEmpty()) {
                val afiliacion = afiliaciones[0]
                val antiguaAfiliacion = MiPersonajesApp.database.afiliacionesDao().afiliacionPorLider(binding.spNuevoLider.selectedItem.toString())
                afiliacion.lider = nuevaAfiliacion
                MiPersonajesApp.database.afiliacionesDao().updateAfiliacion(afiliacion)

                if (antiguaAfiliacion != null && antiguaAfiliacion.lider == binding.spNuevoLider.selectedItem.toString()) {
                    MiPersonajesApp.database.afiliacionesDao().actualizarLiderDeAfiliacion(antiguaAfiliacion.nombreAfiliacion, "Sin lider")
                }

                MiPersonajesApp.database.personajeDao().actualizarAfiliacionPersonaje(binding.spAfiliacion.selectedItem.toString(), binding.spNuevoLider.selectedItem.toString())

                runOnUiThread {
                    //clearTextos()
                    Toast.makeText(this@UpdateLider, "Afiliacion actualizada correctamente", Toast.LENGTH_SHORT).show()
                    //actualizarRecyclerView()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@UpdateLider, "Este personaje no existe en la base de datos", Toast.LENGTH_SHORT).show()
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