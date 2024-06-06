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
import com.example.proyecto_assassinscreed.adapter.PersonajeAdapter
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.Dominio
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirDominioBinding
import com.example.proyecto_assassinscreed.databinding.ActivityListarAfiliacionesBinding
import com.example.proyecto_assassinscreed.databinding.ActivityListarPersonajesBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class ListarAfiliaciones : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    lateinit var binding: ActivityListarAfiliacionesBinding
    lateinit var afiliaciones: MutableList<Afiliaciones>
    lateinit var adapter: AfiliacionAdapter
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarAfiliacionesBinding.inflate(layoutInflater)
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

        afiliaciones = ArrayList()
        getAfiliaciones()

        CoroutineScope(Dispatchers.IO).launch {
            val afiliaciones = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterPersonajes = ArrayAdapter(this@ListarAfiliaciones, R.layout.item_spinner, afiliaciones)
                adapterPersonajes.setDropDownViewResource(R.layout.item_spinner)
                binding.spLideres.adapter = adapterPersonajes
            }
        }

        binding.bActualizarAfiliacion.setOnClickListener {
            val intent = Intent(this, UpdateLider::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
        }

        binding.bFiltro.setOnClickListener {
            filtroPorLider(binding.spLideres.selectedItem.toString())
        }

        binding.bRestablecerFiltro.setOnClickListener {
            getAfiliaciones()
        }

        binding.buscadorAfiliaciones.addTextChangedListener { buscador ->
            val textoBuscador = buscador.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val buscadorAfiliaciones = MiPersonajesApp.database.afiliacionesDao().getAllAfiliaciones().filter { afiliacion ->
                    afiliacion.nombreAfiliacion.contains(textoBuscador, ignoreCase = true)
                }
                runOnUiThread {
                    adapter.actualizarAfiliaciones(buscadorAfiliaciones as MutableList<Afiliaciones>)
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

    fun getAfiliaciones() {
        CoroutineScope(Dispatchers.IO).launch {
            afiliaciones = MiPersonajesApp.database.afiliacionesDao().getAllAfiliaciones()
            runOnUiThread {
                adapter = AfiliacionAdapter(afiliaciones, { deleteAfiliacion(it) })
                recycler = binding.recyclerAfiliaciones
                recycler.layoutManager = LinearLayoutManager(this@ListarAfiliaciones)
                recycler.adapter = adapter
            }
        }
    }

    fun deleteAfiliacion(afiliacion: Afiliaciones) {
        CoroutineScope(Dispatchers.IO).launch {
            val position = afiliaciones.indexOf(afiliacion)
            MiPersonajesApp.database.personajeDao().actualizarPersonajes("Personaje Libre", afiliacion.nombreAfiliacion)
            MiPersonajesApp.database.afiliacionesDao().deleteAfiliacion(afiliacion)
            afiliaciones.remove(afiliacion)
            runOnUiThread {
                adapter.notifyItemRemoved(position)
                getAfiliaciones()
            }
        }
    }

    fun filtroPorLider(lider: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val afiliaciones = MiPersonajesApp.database.afiliacionesDao().filtrarPorLider(lider)
            runOnUiThread {
                adapter.actualizarAfiliaciones(afiliaciones)
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