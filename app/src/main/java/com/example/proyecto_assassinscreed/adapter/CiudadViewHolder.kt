package com.example.proyecto_assassinscreed.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.Ciudades
import com.example.proyecto_assassinscreed.databinding.ItemAfiliacionBinding
import com.example.proyecto_assassinscreed.databinding.ItemCiudadBinding

class CiudadViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val bindingCiudades = ItemCiudadBinding.bind(view)

    fun render(ciudadesModel: Ciudades) {
        bindingCiudades.ciudadName.text = ciudadesModel.ciudad
        bindingCiudades.gobernador.text = ciudadesModel.gobernador
        bindingCiudades.isla.text = ciudadesModel.isla
        bindingCiudades.dominio.text = ciudadesModel.dominio
        bindingCiudades.descripcionCiudad.text = ciudadesModel.descripcion
    }

    fun bind(ciudad: Ciudades) {
        bindingCiudades.ciudadName.text = ciudad.ciudad
        bindingCiudades.gobernador.text = ciudad.gobernador
        bindingCiudades.isla.text = ciudad.isla
        bindingCiudades.dominio.text = ciudad.dominio
        bindingCiudades.descripcionCiudad.text = ciudad.descripcion
    }
}