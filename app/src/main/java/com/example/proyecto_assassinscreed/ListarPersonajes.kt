package com.example.proyecto_assassinscreed

import android.app.Person
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.adapter.PersonajeAdapter
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirPersonajeBinding
import com.example.proyecto_assassinscreed.databinding.ActivityListarPersonajesBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListarPersonajes : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    lateinit var binding: ActivityListarPersonajesBinding
    lateinit var personajes: MutableList<Personajes>
    lateinit var adapter: PersonajeAdapter
    lateinit var recycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarPersonajesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbarMenu)
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        personajes = ArrayList()
        getPersonajes()

        /*binding.buscador.addTextChangedListener {buscador ->
            val buscadorPersonajes = MiPersonajesApp.database.personajeDao().getAllPersonajes().filter {personaje ->
                personaje.nombrePersonaje.contains(buscador.toString(), ignoreCase = true) }
            adapter.actualizarPersonajes(buscadorPersonajes as MutableList<Personajes>)
        }*/

        binding.buscador.addTextChangedListener { buscador ->
            val textoBuscador = buscador.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val buscadorPersonajes = MiPersonajesApp.database.personajeDao().getAllPersonajes().filter { personaje ->
                    personaje.nombrePersonaje.contains(textoBuscador, ignoreCase = true)
                }
                runOnUiThread {
                    adapter.actualizarPersonajes(buscadorPersonajes as MutableList<Personajes>)
                }
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inicio -> {
                val intent = Intent(this, InicioActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)

            }

            R.id.insertarPersonaje -> {
                val intent = Intent(this, AnnadirPersonaje::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)

            }

            R.id.insertarAfiliacion -> {
                val intent = Intent(this, AnnadirAfiliacion::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)

            }

            R.id.insertarCiudad -> {
                val intent = Intent(this, AnnadirCiudad::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)

            }

            R.id.insertarDominio -> {
                val intent = Intent(this, AnnadirDominio::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)

            }

            R.id.listarPersonaje -> {
                val intent = Intent(this, ListarPersonajes::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            }

            R.id.listarAfiliacion -> {
                val intent = Intent(this, ListarAfiliaciones::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            }
        }
        drawer.closeDrawers()
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
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