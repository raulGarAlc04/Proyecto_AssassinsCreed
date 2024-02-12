package com.example.proyecto_assassinscreed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto_assassinscreed.databinding.ActivityMainBinding
import com.example.proyecto_assassinscreed.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth

class RegistroActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_registro)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Nuevo usuario"
        binding.bRegistrar.setOnClickListener {
            if (binding.mail.text.isNotEmpty() && binding.passwd.text.isNotEmpty() && binding.nombre.text.isNotEmpty() && binding.apellidos.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.mail.text.toString(),
                    binding.passwd.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, InicioActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Error en el registro del usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else { Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show() }
        }
    }
}