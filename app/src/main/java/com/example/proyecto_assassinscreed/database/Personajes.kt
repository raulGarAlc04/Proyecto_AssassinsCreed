package com.example.proyecto_assassinscreed.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personajes")
data class Personajes(
    @PrimaryKey
    var nombrePersonaje:String = "",
    var anioFallecimiento:String = "",
    var lugarFallecimiento:String = "",
    var afiliacion:String = "",
    var villano:Boolean = false
)
