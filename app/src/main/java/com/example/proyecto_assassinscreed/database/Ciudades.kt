package com.example.proyecto_assassinscreed.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ciudades")
data class Ciudades(
    @PrimaryKey
    var ciudad:String = "",
    var gobernador:String = "",
    var isla:String = "",
    var dominio:String = "",
    var descripcion:String = ""
)
