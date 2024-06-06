package com.example.proyecto_assassinscreed

import android.app.AlertDialog
import android.app.Person
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.adapter.PersonajeAdapter
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityListarPersonajesBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

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

        CoroutineScope(Dispatchers.IO).launch {
            val nombres = MiPersonajesApp.database.afiliacionesDao().nombresAfiliaciones()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterAfiliaciones = ArrayAdapter(this@ListarPersonajes, R.layout.item_spinner, nombres)
                adapterAfiliaciones.setDropDownViewResource(R.layout.item_spinner)
                binding.spAfiliaciones.adapter = adapterAfiliaciones
            }
        }

        binding.bActualizarPersonajes.setOnClickListener {
            val intent = Intent(this, UpdateAfiliacion::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

        binding.bFiltro.setOnClickListener {
            filtroPorAfiliacion(binding.spAfiliaciones.selectedItem.toString())
        }

        binding.bRestablecerFiltro.setOnClickListener {
            getPersonajes()
        }

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
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)

            }

            R.id.insertarAfiliacion -> {
                val intent = Intent(this, AnnadirAfiliacion::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)

            }

            R.id.insertarCiudad -> {
                val intent = Intent(this, AnnadirCiudad::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)

            }

            R.id.insertarDominio -> {
                val intent = Intent(this, AnnadirDominio::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
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

            R.id.listarCiudad -> {
                val intent = Intent(this, ListarCiudades::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            }

            R.id.listarDominio -> {
                val intent = Intent(this, ListarDominios::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                startActivity(intent)
            }

            R.id.salir -> {
                cuadroSalir()
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
                adapter = PersonajeAdapter(personajes, { deletePersonaje(it) } )
                recycler = binding.recycler
                recycler.layoutManager = LinearLayoutManager(this@ListarPersonajes)
                recycler.adapter = adapter
            }
        }
    }

    fun deletePersonaje(personaje: Personajes) {
        CoroutineScope(Dispatchers.IO).launch {
            val position = personajes.indexOf(personaje)
            MiPersonajesApp.database.personajeDao().deletePersonaje(personaje)
            personajes.remove(personaje)
            MiPersonajesApp.database.personajeDao().actualizarPersonajes("Personaje Libre", personaje.afiliacion)
            MiPersonajesApp.database.afiliacionesDao().deletePorLider(personaje.nombrePersonaje)
            MiPersonajesApp.database.ciudadesDao().deletePorGobernador(personaje.nombrePersonaje)
            MiPersonajesApp.database.dominioDao().deletePorLiderDominio(personaje.nombrePersonaje)
            runOnUiThread {
                adapter.notifyItemRemoved(position)
                getPersonajes()
            }
        }
    }

    fun filtroPorAfiliacion(afiliacion: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val personajes = MiPersonajesApp.database.personajeDao().personajesPorAfiliacion(afiliacion)
            runOnUiThread {
                adapter.actualizarPersonajes(personajes)
            }
        }
    }

    fun cuadroSalir() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Desea salir? Los cambios no guardados se perderán")
            .setPositiveButton("Salir") { dialog, id ->
                finishAffinity()
                exitProcess(0)
            }
            .setNegativeButton("Cancelar") { dialog, id ->
                Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }
}