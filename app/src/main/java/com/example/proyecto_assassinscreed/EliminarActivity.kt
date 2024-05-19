package com.example.proyecto_assassinscreed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_assassinscreed.adapter.PersonajeAdapter
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityEliminarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EliminarActivity : ActivityMenuPersonajes() {
    lateinit var binding: ActivityEliminarBinding
    lateinit var adapter: PersonajeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_eliminar)
        binding = ActivityEliminarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PersonajeAdapter(mutableListOf())

        binding.bBorrar.setOnClickListener {
            if (binding.nombrePersonajeEliminar.text.isNotEmpty()) {
                val nombrePersonaje = binding.nombrePersonajeEliminar.text.toString()
                borrarPersonaje(nombrePersonaje)
            } else {
                Toast.makeText(this, "El campo no puede estar vacio", Toast.LENGTH_SHORT).show()
            }


        }
    }

    fun borrarPersonaje(nombre: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val personaje_borrar = MiPersonajesApp.database.personajeDao().personajePorNombre(nombre)
            if (personaje_borrar.isNotEmpty()) {
                val personajeBorrado = personaje_borrar[0]
                MiPersonajesApp.database.personajeDao().deletePersonaje(personajeBorrado)

                runOnUiThread {
                    clearTextos()
                    Toast.makeText(this@EliminarActivity, "Personaje eliminado correctamente", Toast.LENGTH_SHORT).show()
                    actualizarRecyclerView()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@EliminarActivity, "Personaje no encontrado", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    fun clearTextos() {
        binding.nombrePersonajeEliminar.setText("")
    }

    fun actualizarRecyclerView() {
        CoroutineScope(Dispatchers.IO).launch {
            val personajes = MiPersonajesApp.database.personajeDao().getAllPersonajes()
            runOnUiThread {
                adapter.actualizarPersonajes(personajes)
            }
        }
    }
}