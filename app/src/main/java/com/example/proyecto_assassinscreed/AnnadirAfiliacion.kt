package com.example.proyecto_assassinscreed

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.DBPersonajes
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.PersonajeDAO
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirAfiliacionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnnadirAfiliacion : AppCompatActivity() {
    private lateinit var binding: ActivityAnnadirAfiliacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnadirAfiliacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ejecutar la consulta en un hilo separado usando coroutines
        CoroutineScope(Dispatchers.IO).launch {
            val nombres = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapter = ArrayAdapter(this@AnnadirAfiliacion, android.R.layout.simple_spinner_item, nombres)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spLider.adapter = adapter
            }
        }

        binding.bAnnadirAfiliacion.setOnClickListener {
            if (binding.nombreAfiliacion.text.isNotEmpty() && binding.guarida.text.isNotEmpty() && binding.fechaFundacion.text.isNotEmpty() && binding.descripcion.text.isNotEmpty() &&(!binding.radioCriminal.isChecked || !binding.radioPacifica.isChecked)) {
                CoroutineScope(Dispatchers.IO).launch {
                    val afiliacion = MiPersonajesApp.database.afiliacionesDao().afiliacionPorNombre(binding.nombreAfiliacion.text.toString())
                    if (afiliacion.isEmpty()) {
                        MiPersonajesApp.database.afiliacionesDao().addAfiliacion(
                            Afiliaciones(
                                nombreAfiliacion = binding.nombreAfiliacion.text.toString(),
                                lider = binding.spLider.selectedItem.toString(),
                                guarida = binding.guarida.text.toString(),
                                fechaFundacion = binding.fechaFundacion.text.toString(),
                                descripcion = binding.descripcion.text.toString(),
                                organizacionCriminal = binding.radioCriminal.isChecked
                            )
                        )

                        runOnUiThread {
                            clearTextos()
                            Toast.makeText(this@AnnadirAfiliacion, "Afiliacion insertada", Toast.LENGTH_SHORT).show()
                            //actualizarRecyclerView()

                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@AnnadirAfiliacion, "Esta afiliacion ya está en la base de datos",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this,"Ningun campo puede estar vacío",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clearTextos() {
        binding.nombreAfiliacion.setText("")
        binding.guarida.setText("")
        binding.fechaFundacion.setText("")
        binding.descripcion.setText("")
        binding.radioCriminal.isChecked = false
        binding.radioPacifica.isChecked = false
    }
}
