package com.example.proyecto_assassinscreed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.bAnnadir.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                MiPersonajesApp.database.personajeDao().addPersonaje(
                    Personajes(
                        nombrePersonaje = binding.nombrePersonaje.text.toString(),
                        anioFallecimiento = binding.anioFallecimiento.text.toString(),
                        lugarFallecimiento = binding.lugarFallecimiento.text.toString(),
                        afiliacion = binding.afiliacion.text.toString(),
                        villano = binding.radioVillano.isChecked
                    )
                )
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