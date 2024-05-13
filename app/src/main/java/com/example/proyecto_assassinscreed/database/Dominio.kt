package com.example.proyecto_assassinscreed.database

import androidx.room.Entity

@Entity(tableName = "dominio")
data class Dominio(
    var nombreDominio:String = "",
    var lider:String = "",
    var capital:String = "",
    var criminal:String = "",
    var descripcion:String = "",
)
