package com.example.proyecto_assassinscreed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.R

class CarruselAdapter(private val images: List<Int>): RecyclerView.Adapter<CarruselAdapter.CarruselViewHolder>() {
    inner class CarruselViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imagenCarrusel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarruselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrusel, parent, false)
        return CarruselViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: CarruselViewHolder, position: Int) {
        val curImage= images[position]
        holder.imageView.setImageResource(curImage)
    }
}