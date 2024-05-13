package com.example.proyecto_assassinscreed.database

import androidx.room.Entity

@Entity(tableName = "usuarios")
data class Usuarios(
    var nombreUsuario:String = "",
    var contrasena:String = "",
    var nombre:String = "",
    var apellidos:String = "",
    var admin:Boolean = false
)
