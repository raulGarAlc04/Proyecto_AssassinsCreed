package com.example.proyecto_assassinscreed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.R
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.Ciudades
import com.example.proyecto_assassinscreed.database.Dominio

class DominioAdapter(var dominioList: MutableList<Dominio>): RecyclerView.Adapter<DominioViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DominioViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DominioViewHolder(layoutInflater.inflate(R.layout.item_dominio,parent,false))
    }

    override fun getItemCount(): Int {
        return dominioList.size
    }

    override fun onBindViewHolder(holder: DominioViewHolder, position: Int) {
        val item = dominioList[position]
        holder.bind(item)
    }

    fun actualizarDominio(dominios: MutableList<Dominio>) {
        this.dominioList = dominios
        notifyDataSetChanged()
    }
}