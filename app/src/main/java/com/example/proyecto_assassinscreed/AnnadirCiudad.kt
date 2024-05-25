package com.example.proyecto_assassinscreed

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.Ciudades
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirAfiliacionBinding
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirCiudadBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnnadirCiudad : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var binding: ActivityAnnadirCiudadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnadirCiudadBinding.inflate(layoutInflater)
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

        // Ejecutar la consulta en un hilo separado usando coroutines
        CoroutineScope(Dispatchers.IO).launch {
            val nombresPersonajes = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            val nombresDominios = MiPersonajesApp.database.dominioDao().nombresDiminios()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterPersonajes = ArrayAdapter(this@AnnadirCiudad, android.R.layout.simple_spinner_item, nombresPersonajes)
                val adapterDominios = ArrayAdapter(this@AnnadirCiudad, android.R.layout.simple_spinner_item, nombresDominios)
                adapterPersonajes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapterDominios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spGobernador.adapter = adapterPersonajes
                binding.spDominio.adapter = adapterDominios
            }
        }

        binding.bAnnadirCiudad.setOnClickListener {
            if (binding.nombreCiudad.text.isNotEmpty() && binding.isla.text.isNotEmpty() && binding.descripcionCiudad.text.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    val ciudad = MiPersonajesApp.database.ciudadesDao().ciudadPorNombre(binding.nombreCiudad.text.toString())
                    if (ciudad.isEmpty()) {
                        MiPersonajesApp.database.ciudadesDao().addCiudad(
                            Ciudades(
                                ciudad = binding.nombreCiudad.text.toString(),
                                gobernador = binding.spGobernador.selectedItem.toString(),
                                isla = binding.isla.text.toString(),
                                dominio = binding.spDominio.selectedItem.toString(),
                                descripcion = binding.descripcionCiudad.text.toString()
                            )
                        )

                        runOnUiThread {
                            clearTextos()
                            Toast.makeText(this@AnnadirCiudad, "Ciudad insertada", Toast.LENGTH_SHORT).show()
                            //actualizarRecyclerView()

                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@AnnadirCiudad, "Esta ciudad ya está en la base de datos",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this,"Ningun campo puede estar vacío", Toast.LENGTH_SHORT).show()
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

    fun clearTextos() {
        binding.nombreCiudad.setText("")
        binding.isla.setText("")
        binding.descripcionCiudad.setText("")
    }
}