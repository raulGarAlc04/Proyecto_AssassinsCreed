package com.example.proyecto_assassinscreed

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import com.example.proyecto_assassinscreed.databinding.ActivityInicioBinding
import com.google.android.material.navigation.DrawerLayoutUtils
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class InicioActivity : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener{

    lateinit var drawer: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var imagen: ImageButton
    lateinit var binding: ActivityInicioBinding
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        uri ->
        if (uri != null) {
            imagen.setImageURI(uri)
        } else {
            // Imagen no seleccionada
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarMenu)
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        imagen = binding.bFotoPerfil

        val foto = binding.imagenInicio
        registerForContextMenu(foto)

        binding.btnCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.bFotoPerfil.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> Toast.makeText(this, "item 1", Toast.LENGTH_SHORT).show()
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
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

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.menu_contextual, menu)
        menu.setHeaderTitle("Opciones sobre la imagen")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.copiarImagen -> {
                Toast.makeText(this, "Imagen copiada al portapeles",Toast.LENGTH_SHORT).show()
                true
            }

            R.id.ocultarImagen -> {
                binding.bFotoPerfil.isVisible = false
                Toast.makeText(this, "Foto de perfil ocultada",Toast.LENGTH_SHORT).show()
                true
            }

            R.id.mostrarFoto -> {
                binding.bFotoPerfil.isVisible = true
                Toast.makeText(this, "Foto de perfil visible de nuevo", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }
}