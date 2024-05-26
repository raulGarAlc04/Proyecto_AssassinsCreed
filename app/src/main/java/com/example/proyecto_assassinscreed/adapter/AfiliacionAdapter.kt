package com.example.proyecto_assassinscreed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.R
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.Personajes

class AfiliacionAdapter(var afiliacionList: MutableList<Afiliaciones>): RecyclerView.Adapter<AfiliacionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AfiliacionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AfiliacionViewHolder(layoutInflater.inflate(R.layout.item_afiliacion,parent,false))
    }

    override fun getItemCount(): Int {
        return afiliacionList.size
    }

    override fun onBindViewHolder(holder: AfiliacionViewHolder, position: Int) {
        val item = afiliacionList[position]
        holder.bind(item)
    }

    fun actualizarAfiliaciones(afiliaciones: MutableList<Afiliaciones>) {
        this.afiliacionList = afiliaciones
        notifyDataSetChanged()
    }
}