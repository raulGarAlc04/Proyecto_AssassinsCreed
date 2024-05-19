package com.example.proyecto_assassinscreed.database

import androidx.room.Entity

@Entity(tableName = "dominio")
data class Dominio(
    var nombreDominio:String = "",
    var liderDominio:String = "",
    var capital:String = "",
    var criminal:Boolean = false,
    var descripcion:String = ""
)
