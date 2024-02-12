package com.example.proyecto_assassinscreed.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.proyecto_assassinscreed.Personaje

@Dao
interface PersonajeDAO {
    @Query("SELECT * FROM personajes")
    fun getAllPersonajes(): MutableList<Personajes>

    @Query("SELECT * FROM personajes WHERE nombrePersonaje like :nombre")
    fun personajePorNombre(nombre: String): MutableList<Personajes>

    @Insert
    fun addPersonaje(elemento: Personajes)

    @Delete
    fun deletePersonaje(elemento: Personajes)

    @Update
    fun updatePersonaje(elemento: Personajes)
}