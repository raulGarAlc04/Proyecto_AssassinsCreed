package com.example.proyecto_assassinscreed.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Personajes::class), version = 1)
abstract class DBPersonajes: RoomDatabase() {
    abstract fun personajeDao(): PersonajeDAO
}