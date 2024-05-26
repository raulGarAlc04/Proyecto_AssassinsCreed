package com.example.proyecto_assassinscreed.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.Dominio
import com.example.proyecto_assassinscreed.databinding.ItemAfiliacionBinding
import com.example.proyecto_assassinscreed.databinding.ItemDominioBinding

class DominioViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val bindingDominios = ItemDominioBinding.bind(view)

    fun render(dominiosModel: Dominio) {
        bindingDominios.dominioName.text = dominiosModel.nombreDominio
        bindingDominios.liderDominio.text = dominiosModel.liderDominio
        bindingDominios.capital.text = dominiosModel.capital
        if (dominiosModel.criminal) {
            bindingDominios.domCriminal.text = "Dominio Criminal"
        } else {
            bindingDominios.domCriminal.text = "Dominio Pacifico"
        }
        bindingDominios.descripcionDominio.text = dominiosModel.descripcion
    }

    fun bind(dominio: Dominio) {
        bindingDominios.dominioName.text = dominio.nombreDominio
        bindingDominios.liderDominio.text = dominio.liderDominio
        bindingDominios.capital.text = dominio.capital
        if (dominio.criminal) {
            bindingDominios.domCriminal.text = "Dominio Criminal"
        } else {
            bindingDominios.domCriminal.text = "Dominio Pacifico"
        }
        bindingDominios.descripcionDominio.text = dominio.descripcion
    }
}