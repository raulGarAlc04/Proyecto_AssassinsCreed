package com.example.proyecto_assassinscreed

import android.app.Person
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.adapter.PersonajeAdapter
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirPersonajeBinding
import com.example.proyecto_assassinscreed.databinding.ActivityListarPersonajesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListarPersonajes : ActivityWithMenus() {
    lateinit var binding: ActivityListarPersonajesBinding
    lateinit var personajes: MutableList<Personajes>
    lateinit var adapter: PersonajeAdapter
    lateinit var recycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarPersonajesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personajes = ArrayList()
        getPersonajes()

        binding.buscador.addTextChangedListener {buscador ->
            val buscadorPersonajes = MiPersonajesApp.database.personajeDao().getAllPersonajes().filter {personaje ->
                personaje.nombrePersonaje.contains(buscador.toString()) }
            adapter.actualizarPersonajes(buscadorPersonajes as MutableList<Personajes>)
        }
    }

    fun getPersonajes() {
        CoroutineScope(Dispatchers.IO).launch {
            personajes = MiPersonajesApp.database.personajeDao().getAllPersonajes()
            runOnUiThread {
                adapter = PersonajeAdapter(personajes)
                recycler = binding.recycler
                recycler.layoutManager = LinearLayoutManager(this@ListarPersonajes)
                recycler.adapter = adapter
            }
        }
    }
}