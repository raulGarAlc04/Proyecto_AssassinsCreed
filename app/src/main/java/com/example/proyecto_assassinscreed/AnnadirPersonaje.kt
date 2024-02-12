package com.example.proyecto_assassinscreed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_assassinscreed.adapter.PersonajeAdapter
import com.example.proyecto_assassinscreed.database.DBPersonajes
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirPersonajeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnnadirPersonaje : ActivityWithMenus() {
    lateinit var binding : ActivityAnnadirPersonajeBinding
    lateinit var adapter: PersonajeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_annadir_personaje)
        binding = ActivityAnnadirPersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cuadroDialogo = CuadroDialogo()

        binding.bAnnadir.setOnClickListener {
            if (binding.nombrePersonaje.text.isNotEmpty() && binding.anioFallecimiento.text.isNotEmpty() && binding.lugarFallecimiento.text.isNotEmpty() && binding.afiliacion.text.isNotEmpty() && (!binding.radioVillano.isChecked || !binding.radioAmigo.isChecked)) {
                CoroutineScope(Dispatchers.IO).launch {
                    val personaje = MiPersonajesApp.database.personajeDao().personajePorNombre(binding.nombrePersonaje.text.toString())
                    if (personaje.isEmpty()) {
                        MiPersonajesApp.database.personajeDao().addPersonaje(
                            Personajes(
                                nombrePersonaje = binding.nombrePersonaje.text.toString(),
                                anioFallecimiento = binding.anioFallecimiento.text.toString(),
                                lugarFallecimiento = binding.lugarFallecimiento.text.toString(),
                                afiliacion = binding.afiliacion.text.toString(),
                                villano = binding.radioVillano.isChecked
                            )
                        )

                        runOnUiThread {
                            clearTextos()
                            Toast.makeText(this@AnnadirPersonaje, "Personaje insertado",Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@AnnadirPersonaje, "Este personaje ya está en la base de datos",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this,"Ningun campo puede estar vacío",Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun clearTextos() {
        binding.nombrePersonaje.setText("")
        binding.anioFallecimiento.setText("")
        binding.lugarFallecimiento.setText("")
        binding.afiliacion.setText("")
        binding.radioVillano.isChecked = false
        binding.radioAmigo.isChecked = false
    }

}