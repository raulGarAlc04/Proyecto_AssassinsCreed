package com.example.proyecto_assassinscreed

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.proyecto_assassinscreed.database.Ciudades
import com.example.proyecto_assassinscreed.database.Dominio
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirCiudadBinding
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirDominioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnnadirDominio : ActivityMenuDominios() {
    private lateinit var binding: ActivityAnnadirDominioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnadirDominioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ejecutar la consulta en un hilo separado usando coroutines
        CoroutineScope(Dispatchers.IO).launch {
            val nombresPersonajesLider = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            val nombresCapital = MiPersonajesApp.database.ciudadesDao().nombresCiudades()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterPersonajesLider = ArrayAdapter(this@AnnadirDominio, R.layout.simple_spinner_item, nombresPersonajesLider)
                val adapterCapital = ArrayAdapter(this@AnnadirDominio, R.layout.simple_spinner_item, nombresCapital)
                adapterPersonajesLider.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                adapterCapital.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.spLiderDominio.adapter = adapterPersonajesLider
                binding.spCapital.adapter = adapterCapital
            }
        }

        binding.bAnnadirDominio.setOnClickListener {
            if (binding.nombreDominio.text.isNotEmpty() && binding.descripcion.text.isNotEmpty() && (!binding.radioCriminal.isChecked || !binding.radioPacifica.isChecked)) {
                CoroutineScope(Dispatchers.IO).launch {
                    val dominio = MiPersonajesApp.database.ciudadesDao().ciudadPorNombre(binding.nombreDominio.text.toString())
                    if (dominio.isEmpty()) {
                        MiPersonajesApp.database.dominioDao().addDominio(
                            Dominio(
                                nombreDominio = binding.nombreDominio.text.toString(),
                                liderDominio = binding.spLiderDominio.selectedItem.toString(),
                                capital = binding.spCapital.selectedItem.toString(),
                                descripcion = binding.nombreDominio.text.toString(),
                                criminal = binding.radioCriminal.isChecked
                            )
                        )

                        runOnUiThread {
                            clearTextos()
                            Toast.makeText(this@AnnadirDominio, "Dominio insertado", Toast.LENGTH_SHORT).show()
                            //actualizarRecyclerView()

                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@AnnadirDominio, "Este dominio ya está en la base de datos",
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
        binding.nombreDominio.setText("")
        binding.descripcion.setText("")
        binding.radioCriminal.isChecked = false
        binding.radioPacifica.isChecked = false
    }
}