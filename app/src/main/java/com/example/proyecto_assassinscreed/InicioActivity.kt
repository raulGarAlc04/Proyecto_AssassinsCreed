package com.example.proyecto_assassinscreed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyecto_assassinscreed.databinding.ActivityInicioBinding
import com.google.firebase.auth.FirebaseAuth

class InicioActivity : ActivityWithMenus() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}