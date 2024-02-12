package com.example.proyecto_assassinscreed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityEliminarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EliminarActivity : ActivityWithMenus() {
    lateinit var binding: ActivityEliminarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_eliminar)
        binding = ActivityEliminarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bBorrar.setOnClickListener {
            val nombrePersonaje = binding.nombrePersonajeEliminar.text.toString()

            borrarPersonaje(nombrePersonaje)
        }
    }

    fun borrarPersonaje(nombre: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val personaje_borrar = MiPersonajesApp.database.personajeDao().personajePorNombre(nombre)
            val personajeBorrado = personaje_borrar[0]

            MiPersonajesApp.database.personajeDao().deletePersonaje(personajeBorrado)
        }
    }
}