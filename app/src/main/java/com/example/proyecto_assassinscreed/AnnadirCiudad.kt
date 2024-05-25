package com.example.proyecto_assassinscreed

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.Ciudades
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirAfiliacionBinding
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirCiudadBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnnadirCiudad : AppCompatActivity() {
    private lateinit var binding: ActivityAnnadirCiudadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnadirCiudadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ejecutar la consulta en un hilo separado usando coroutines
        CoroutineScope(Dispatchers.IO).launch {
            val nombresPersonajes = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            val nombresDominios = MiPersonajesApp.database.dominioDao().nombresDiminios()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterPersonajes = ArrayAdapter(this@AnnadirCiudad, R.layout.simple_spinner_item, nombresPersonajes)
                val adapterDominios = ArrayAdapter(this@AnnadirCiudad, R.layout.simple_spinner_item, nombresDominios)
                adapterPersonajes.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                adapterDominios.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.spGobernador.adapter = adapterPersonajes
                binding.spDominio.adapter = adapterDominios
            }
        }

        binding.bAnnadirCiudad.setOnClickListener {
            if (binding.nombreCiudad.text.isNotEmpty() && binding.isla.text.isNotEmpty() && binding.descripcionCiudad.text.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val ciudad = MiPersonajesApp.database.ciudadesDao().ciudadPorNombre(binding.nombreCiudad.text.toString())
                    if (ciudad.isEmpty()) {
                        MiPersonajesApp.database.ciudadesDao().addCiudad(
                            Ciudades(
                                ciudad = binding.nombreCiudad.text.toString(),
                                gobernador = binding.spGobernador.selectedItem.toString(),
                                isla = binding.isla.text.toString(),
                                dominio = binding.spDominio.selectedItem.toString(),
                                descripcion = binding.descripcionCiudad.text.toString()
                            )
                        )

                        runOnUiThread {
                            clearTextos()
                            Toast.makeText(this@AnnadirCiudad, "Ciudad insertada", Toast.LENGTH_SHORT).show()
                            //actualizarRecyclerView()

                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@AnnadirCiudad, "Esta ciudad ya está en la base de datos",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this,"Ningun campo puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clearTextos() {
        binding.nombreCiudad.setText("")
        binding.isla.setText("")
        binding.descripcionCiudad.setText("")
    }
}