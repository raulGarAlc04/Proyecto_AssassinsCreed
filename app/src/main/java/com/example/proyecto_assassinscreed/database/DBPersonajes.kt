package com.example.proyecto_assassinscreed.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Personajes::class, Afiliaciones::class, Ciudades::class, Dominio::class, Usuarios::class), version = 1)
abstract class DBPersonajes: RoomDatabase() {
    abstract fun personajeDao(): PersonajeDAO
    abstract fun afiliacionesDao(): AfiliacionesDAO
    abstract fun ciudadesDao(): CiudadesDAO
    abstract fun dominioDao(): DominioDAO
    abstract fun usuariosDao(): UsuariosDAO
}