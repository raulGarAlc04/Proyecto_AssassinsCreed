package com.example.proyecto_assassinscreed.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuarios(
    @PrimaryKey
    var nombreUsuario:String = "",
    var contrasena:String = "",
    var nombre:String = "",
    var apellidos:String = "",
    var admin:Boolean = false
)
