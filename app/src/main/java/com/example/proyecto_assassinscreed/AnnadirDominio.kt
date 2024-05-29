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
import com.example.proyecto_assassinscreed.database.Ciudades
import com.example.proyecto_assassinscreed.database.Dominio
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirCiudadBinding
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirDominioBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnnadirDominio : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var binding: ActivityAnnadirDominioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnadirDominioBinding.inflate(layoutInflater)
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
            val nombresPersonajesLider = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            val nombresCapital = MiPersonajesApp.database.ciudadesDao().nombresCiudades()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapterPersonajesLider = ArrayAdapter(this@AnnadirDominio, android.R.layout.simple_spinner_item, nombresPersonajesLider)
                val adapterCapital = ArrayAdapter(this@AnnadirDominio, android.R.layout.simple_spinner_item, nombresCapital)
                adapterPersonajesLider.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapterCapital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spLiderDominio.adapter = adapterPersonajesLider
                binding.spCapital.adapter = adapterCapital
            }
        }

        binding.bAnnadirDominio.setOnClickListener {
            if (binding.nombreDominio.text.isNotEmpty() && binding.descripcion.text.isNotEmpty() && (!binding.radioCriminal.isChecked || !binding.radioPacifica.isChecked)) {
                CoroutineScope(Dispatchers.IO).launch {
                    val dominio = MiPersonajesApp.database.ciudadesDao().ciudadPorNombre(binding.nombreDominio.text.toString())
                    if (dominio.isEmpty()) {
                        MiPersonajesApp.database.dominioDao().addDominio(
                            Dominio(
                                nombreDominio = binding.nombreDominio.text.toString(),
                                liderDominio = binding.spLiderDominio.selectedItem.toString(),
                                capital = binding.spCapital.selectedItem.toString(),
                                descripcion = binding.nombreDominio.text.toString(),
                                criminal = binding.radioCriminal.isChecked
                            )
                        )

                        runOnUiThread {
                            clearTextos()
                            Toast.makeText(this@AnnadirDominio, "Dominio insertado", Toast.LENGTH_SHORT).show()
                            //actualizarRecyclerView()

                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@AnnadirDominio, "Este dominio ya está en la base de datos",
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

    fun clearTextos() {
        binding.nombreDominio.setText("")
        binding.descripcion.setText("")
        binding.radioCriminal.isChecked = false
        binding.radioPacifica.isChecked = false
    }
}