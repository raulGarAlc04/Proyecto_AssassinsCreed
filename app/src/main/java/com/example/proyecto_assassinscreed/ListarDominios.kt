package com.example.proyecto_assassinscreed

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.adapter.CiudadAdapter
import com.example.proyecto_assassinscreed.adapter.DominioAdapter
import com.example.proyecto_assassinscreed.database.Ciudades
import com.example.proyecto_assassinscreed.database.Dominio
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityListarCiudadesBinding
import com.example.proyecto_assassinscreed.databinding.ActivityListarDominiosBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListarDominios : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    lateinit var binding: ActivityListarDominiosBinding
    lateinit var dominios: MutableList<Dominio>
    lateinit var adapter: DominioAdapter
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarDominiosBinding.inflate(layoutInflater)
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

        dominios = ArrayList()
        getDominios()

        CoroutineScope(Dispatchers.IO).launch {
            val lideresDominio = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterLideresDominio = ArrayAdapter(this@ListarDominios, android.R.layout.simple_spinner_item, lideresDominio)
                adapterLideresDominio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spLideresDominios.adapter = adapterLideresDominio
            }
        }

        binding.bActualizarDominio.setOnClickListener {
            val intent = Intent(this, UpdateDominio::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

        binding.bFiltro.setOnClickListener {
            filtroPorLiderDominio(binding.spLideresDominios.selectedItem.toString())
        }

        binding.bRestablecerFiltro.setOnClickListener {
            getDominios()
        }

        binding.buscadorDominios.addTextChangedListener { buscador ->
            val textoBuscador = buscador.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val buscadorDominios = MiPersonajesApp.database.dominioDao().getAllDominios().filter { dominio ->
                    dominio.nombreDominio.contains(textoBuscador, ignoreCase = true)
                }
                runOnUiThread {
                    adapter.actualizarDominio(buscadorDominios as MutableList<Dominio>)
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inicio -> {
                val intent = Intent(this, InicioActivity::class.java)
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
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

    fun getDominios() {
        CoroutineScope(Dispatchers.IO).launch {
            dominios = MiPersonajesApp.database.dominioDao().getAllDominios()
            runOnUiThread {
                adapter = DominioAdapter(dominios, { deleteDominio(it) })
                recycler = binding.recyclerDominios
                recycler.layoutManager = LinearLayoutManager(this@ListarDominios)
                recycler.adapter = adapter
            }
        }
    }

    fun deleteDominio(dominio: Dominio) {
        CoroutineScope(Dispatchers.IO).launch {
            val position = dominios.indexOf(dominio)
            MiPersonajesApp.database.dominioDao().deleteDominio(dominio)
            dominios.remove(dominio)
            runOnUiThread {
                adapter.notifyItemRemoved(position)
            }
        }
    }

    fun filtroPorLiderDominio(liderDominio: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val dominios = MiPersonajesApp.database.dominioDao().filtrarPorLiderDominio(liderDominio)
            runOnUiThread {
                adapter.actualizarDominio(dominios)
            }
        }
    }
}