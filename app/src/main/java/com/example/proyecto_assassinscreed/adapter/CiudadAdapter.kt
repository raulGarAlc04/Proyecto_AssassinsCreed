package com.example.proyecto_assassinscreed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.R
import com.example.proyecto_assassinscreed.database.Ciudades

class CiudadAdapter(var ciudadList: MutableList<Ciudades>, val deleteCiudadLista: (Ciudades) -> Unit): RecyclerView.Adapter<CiudadViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CiudadViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CiudadViewHolder(layoutInflater.inflate(R.layout.item_ciudad,parent,false))
    }

    override fun getItemCount(): Int {
        return ciudadList.size
    }

    override fun onBindViewHolder(holder: CiudadViewHolder, position: Int) {
        val item = ciudadList[position]
        holder.bind(item, deleteCiudadLista)
    }

    fun actualizarCiudades(ciudades: MutableList<Ciudades>) {
        this.ciudadList = ciudades
        notifyDataSetChanged()
    }
}