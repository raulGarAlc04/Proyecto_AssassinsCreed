package com.example.proyecto_assassinscreed

import android.app.AlertDialog
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
import com.example.proyecto_assassinscreed.adapter.AfiliacionAdapter
import com.example.proyecto_assassinscreed.adapter.CiudadAdapter
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.Ciudades
import com.example.proyecto_assassinscreed.database.Dominio
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityListarAfiliacionesBinding
import com.example.proyecto_assassinscreed.databinding.ActivityListarCiudadesBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class ListarCiudades : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    lateinit var binding: ActivityListarCiudadesBinding
    lateinit var ciudades: MutableList<Ciudades>
    lateinit var adapter: CiudadAdapter
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarCiudadesBinding.inflate(layoutInflater)
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

        ciudades = ArrayList()
        getCiudades()

        CoroutineScope(Dispatchers.IO).launch {
            val dominios = MiPersonajesApp.database.dominioDao().nombresDiminios()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterCiudades = ArrayAdapter(this@ListarCiudades, R.layout.item_spinner, dominios)
                adapterCiudades.setDropDownViewResource(R.layout.item_spinner)
                binding.spGobernadores.adapter = adapterCiudades
            }
        }

        binding.bActualizarCiudad.setOnClickListener {
            val intent = Intent(this, UpdateCiudad::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

        binding.bFiltro.setOnClickListener {
            filtroPorDominio(binding.spGobernadores.selectedItem.toString())
        }

        binding.bRestablecerFiltro.setOnClickListener {
            getCiudades()
        }

        binding.buscadorCiudades.addTextChangedListener { buscador ->
            val textoBuscador = buscador.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val buscadorCiudades = MiPersonajesApp.database.ciudadesDao().getAllCiudades().filter { ciudad ->
                    ciudad.ciudad.contains(textoBuscador, ignoreCase = true)
                }
                runOnUiThread {
                    adapter.actualizarCiudades(buscadorCiudades as MutableList<Ciudades>)
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

    fun getCiudades() {
        CoroutineScope(Dispatchers.IO).launch {
            ciudades = MiPersonajesApp.database.ciudadesDao().getAllCiudades()
            runOnUiThread {
                adapter = CiudadAdapter(ciudades, { deleteCiudad(it) })
                recycler = binding.recyclerCiudades
                recycler.layoutManager = LinearLayoutManager(this@ListarCiudades)
                recycler.adapter = adapter
            }
        }
    }

    fun deleteCiudad(ciudad: Ciudades) {
        CoroutineScope(Dispatchers.IO).launch {
            val position = ciudades.indexOf(ciudad)
            MiPersonajesApp.database.ciudadesDao().deleteCiudad(ciudad)
            val esCapital = MiPersonajesApp.database.ciudadesDao().esCapital(ciudad.ciudad)
            if (esCapital) {
                MiPersonajesApp.database.ciudadesDao().updateCiudad("Ciudad independiente", ciudad.dominio)
            }
            MiPersonajesApp.database.dominioDao().deletePorCapital(ciudad.ciudad)
            ciudades.remove(ciudad)
            runOnUiThread {
                adapter.notifyItemRemoved(position)
                getCiudades()
            }
        }
    }

    fun filtroPorDominio(dominio: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val ciudades = MiPersonajesApp.database.ciudadesDao().filtrarPorDominio(dominio)
            runOnUiThread {
                adapter.actualizarCiudades(ciudades)
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