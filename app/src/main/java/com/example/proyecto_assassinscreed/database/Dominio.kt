package com.example.proyecto_assassinscreed.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dominio")
data class Dominio(
    @PrimaryKey
    var nombreDominio:String = "",
    var liderDominio:String = "",
    var capital:String = "",
    var criminal:Boolean = false,
    var descripcion:String = ""
)
