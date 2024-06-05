package com.example.proyecto_assassinscreed

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.drawerlayout.widget.DrawerLayout
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.DBPersonajes
import com.example.proyecto_assassinscreed.database.MiPersonajesApp
import com.example.proyecto_assassinscreed.database.PersonajeDAO
import com.example.proyecto_assassinscreed.database.Personajes
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirAfiliacionBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.exitProcess

class AnnadirAfiliacion : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var binding: ActivityAnnadirAfiliacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnadirAfiliacionBinding.inflate(layoutInflater)
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
            val nombres = MiPersonajesApp.database.personajeDao().nombresPersonajes()
            withContext(Dispatchers.Main) {
                // Crear el adaptador y asignarlo al Spinner
                val adapter = ArrayAdapter(this@AnnadirAfiliacion, R.layout.item_spinner, nombres)
                adapter.setDropDownViewResource(R.layout.item_spinner)
                binding.spLider.adapter = adapter
            }
        }

        binding.bAnnadirAfiliacion.setOnClickListener {
            if (binding.nombreAfiliacion.text.isNotEmpty() && binding.guarida.text.isNotEmpty() && binding.fechaFundacion.text.isNotEmpty() && binding.descripcion.text.isNotEmpty() && (!binding.radioCriminal.isChecked || !binding.radioPacifica.isChecked)) {
                // Verificación de la longitud de los campos
                if (binding.nombreAfiliacion.text.length > 20) {
                    Toast.makeText(this, "El nombre de la afiliación no puede tener más de 20 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (binding.guarida.text.length > 20) {
                    Toast.makeText(this, "La guarida no puede tener más de 20 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (binding.fechaFundacion.text.length > 6) { // Se asume que la longitud exacta debe ser 6
                    Toast.makeText(this, "La fecha debe tener maximo 6 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (binding.descripcion.text.length > 20) {
                    Toast.makeText(this, "La descripción no puede tener más de 20 caracteres", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                CoroutineScope(Dispatchers.IO).launch {
                    val afiliacion = MiPersonajesApp.database.afiliacionesDao().afiliacionPorNombre(binding.nombreAfiliacion.text.toString())
                    if (afiliacion.isEmpty()) {
                        var lider: String = ""
                        lider = if (binding.spLider.isNotEmpty()) {
                            binding.spLider.selectedItem.toString()
                        } else {
                            "Sin lider"
                        }
                        MiPersonajesApp.database.afiliacionesDao().addAfiliacion(
                            Afiliaciones(
                                nombreAfiliacion = binding.nombreAfiliacion.text.toString(),
                                lider = lider,
                                guarida = binding.guarida.text.toString(),
                                fechaFundacion = binding.fechaFundacion.text.toString(),
                                descripcion = binding.descripcion.text.toString(),
                                organizacionCriminal = binding.radioCriminal.isChecked
                            )
                        )

                        runOnUiThread {
                            clearTextos()
                            Toast.makeText(this@AnnadirAfiliacion, "Afiliacion insertada", Toast.LENGTH_SHORT).show()
                            //actualizarRecyclerView()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@AnnadirAfiliacion, "Esta afiliacion ya está en la base de datos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Ningun campo puede estar vacío", Toast.LENGTH_SHORT).show()
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
        binding.nombreAfiliacion.setText("")
        binding.guarida.setText("")
        binding.fechaFundacion.setText("")
        binding.descripcion.setText("")
        binding.radioCriminal.isChecked = false
        binding.radioPacifica.isChecked = false
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
