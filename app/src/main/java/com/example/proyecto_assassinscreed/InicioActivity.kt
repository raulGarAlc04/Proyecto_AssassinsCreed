package com.example.proyecto_assassinscreed

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.example.proyecto_assassinscreed.databinding.ActivityInicioBinding
import com.google.firebase.auth.FirebaseAuth

class InicioActivity : ActivityWithMenus() {
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