package com.example.proyecto_assassinscreed

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.isNotEmpty
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.adapter.PersonajeAdapter
import com.example.proyecto_assassinscreed.database.DBPersonajes
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirPersonajeBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class AnnadirPersonaje : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    lateinit var binding : ActivityAnnadirPersonajeBinding
    //lateinit var adapter: PersonajeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_annadir_personaje)
        binding = ActivityAnnadirPersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //adapter = PersonajeAdapter(mutableListOf(), )

        toolbar = findViewById(R.id.toolbarMenu)
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        CoroutineScope(Dispatchers.IO).launch {
            val nombres = MiPersonajesApp.database.afiliacionesDao().nombresAfiliaciones()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapter = ArrayAdapter(this@AnnadirPersonaje, R.layout.item_spinner, nombres)
                adapter.setDropDownViewResource(R.layout.item_spinner)
                binding.spAfiliacion.adapter = adapter
            }
        }

        binding.bAnnadir.setOnClickListener {
            if (binding.nombrePersonaje.text.isNotEmpty() && binding.anioFallecimiento.text.isNotEmpty() && binding.lugarFallecimiento.text.isNotEmpty() && (!binding.radioVillano.isChecked || !binding.radioAmigo.isChecked)) {
                if (binding.nombrePersonaje.text.length > 20) {
                    Toast.makeText(this, "El nombre del personaje no puede tener más de 20 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (binding.anioFallecimiento.text.length > 20) {
                    Toast.makeText(this, "El año de fallecimiento no puede tener más de 20 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (binding.lugarFallecimiento.text.length > 20) { // Se asume que la longitud exacta debe ser 6
                    Toast.makeText(this, "El lugar de fallecimiento debe tener maximo 20 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                CoroutineScope(Dispatchers.IO).launch {
                    val personaje = MiPersonajesApp.database.personajeDao().personajePorNombre(binding.nombrePersonaje.text.toString())
                    if (personaje.isEmpty()) {
                        var afiliacion: String = ""
                        afiliacion = if (binding.spAfiliacion.isNotEmpty()) {
                            binding.spAfiliacion.selectedItem.toString()
                        } else {
                            "Personaje Libre"
                        }
                        MiPersonajesApp.database.personajeDao().addPersonaje(
                            Personajes(
                                nombrePersonaje = binding.nombrePersonaje.text.toString(),
                                anioFallecimiento = binding.anioFallecimiento.text.toString(),
                                lugarFallecimiento = binding.lugarFallecimiento.text.toString(),
                                afiliacion = afiliacion,
                                villano = binding.radioVillano.isChecked
                            )
                        )

                        runOnUiThread {
                            clearTextos()
                            Toast.makeText(this@AnnadirPersonaje, "Personaje insertado",Toast.LENGTH_SHORT).show()
                            //actualizarRecyclerView()

                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@AnnadirPersonaje, "Este personaje ya está en la base de datos",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this,"Ningun campo puede estar vacío",Toast.LENGTH_SHORT).show()
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

    fun clearTextos() {
        binding.nombrePersonaje.setText("")
        binding.anioFallecimiento.setText("")
        binding.lugarFallecimiento.setText("")
        binding.radioVillano.isChecked = false
        binding.radioAmigo.isChecked = false
    }

    /*fun actualizarRecyclerView() {
        CoroutineScope(Dispatchers.IO).launch {
            val personajes = MiPersonajesApp.database.personajeDao().getAllPersonajes()
            runOnUiThread {
                adapter.actualizarPersonajes(personajes)
            }
        }
    }*/

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