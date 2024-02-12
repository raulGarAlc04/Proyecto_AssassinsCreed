package com.example.proyecto_assassinscreed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_assassinscreed.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIniciarSesion.setOnClickListener {
            login()
        }

        binding.btnRegistrar.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    private fun login() {
        if (binding.email.text.isNotEmpty() && binding.password.text.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, InicioActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
        } else { Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show() }
    }
}