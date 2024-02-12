package com.example.proyecto_assassinscreed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_assassinscreed.adapter.PersonajeAdapter
import com.example.proyecto_assassinscreed.databinding.ActivityAnnadirPersonajeBinding
import com.example.proyecto_assassinscreed.databinding.ActivityListarPersonajesBinding

class ListarPersonajes : ActivityWithMenus() {
    lateinit var binding: ActivityListarPersonajesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarPersonajesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = LinearLayoutManager(this)
    }
}