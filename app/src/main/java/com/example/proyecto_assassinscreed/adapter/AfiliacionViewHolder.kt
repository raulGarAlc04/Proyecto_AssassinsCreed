package com.example.proyecto_assassinscreed.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.databinding.ItemAfiliacionBinding

class AfiliacionViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val bindingAfiliaciones = ItemAfiliacionBinding.bind(view)

    fun render(afiliacionesModel: Afiliaciones) {
        bindingAfiliaciones.afiliacionName.text = afiliacionesModel.nombreAfiliacion
        bindingAfiliaciones.lider.text = afiliacionesModel.lider
        bindingAfiliaciones.guarida.text = afiliacionesModel.guarida
        bindingAfiliaciones.fechaFundacionAf.text = afiliacionesModel.fechaFundacion
        bindingAfiliaciones.descripcionAfiliacion.text = afiliacionesModel.descripcion
        if (afiliacionesModel.organizacionCriminal) {
            bindingAfiliaciones.orgCriminal.text = "Organizacion Criminal"
        } else {
            bindingAfiliaciones.orgCriminal.text = "Organizacion Pacifica"
        }
    }

    fun bind(afiliacion: Afiliaciones, deleteAfiliacionLista: (Afiliaciones) -> Unit) {
        bindingAfiliaciones.afiliacionName.text = afiliacion.nombreAfiliacion
        bindingAfiliaciones.lider.text = afiliacion.lider
        bindingAfiliaciones.guarida.text = afiliacion.guarida
        bindingAfiliaciones.fechaFundacionAf.text = afiliacion.fechaFundacion
        bindingAfiliaciones.descripcionAfiliacion.text = afiliacion.descripcion
        if (afiliacion.organizacionCriminal) {
            bindingAfiliaciones.orgCriminal.text = "Organizacion Criminal"
        } else {
            bindingAfiliaciones.orgCriminal.text = "Organizacion Pacifica"
        }

        bindingAfiliaciones.trashButton.setOnClickListener { deleteAfiliacionLista(afiliacion) }
    }
}