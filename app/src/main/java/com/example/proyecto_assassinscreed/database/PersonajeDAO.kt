package com.example.proyecto_assassinscreed.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonajeDAO {
    @Query("SELECT * FROM personajes")
    fun getAllPersonajes(): MutableList<Personajes>

    @Query("SELECT * FROM personajes WHERE nombrePersonaje like :nombre")
    fun personajePorNombre(nombre: String): MutableList<Personajes>

    @Query("SELECT nombrePersonaje FROM personajes")
    fun nombresPersonajes(): MutableList<String>

    @Query("SELECT * FROM personajes WHERE afiliacion like :afiliacion")
    fun personajesPorAfiliacion(afiliacion: String): MutableList<Personajes>

    @Query("UPDATE personajes SET afiliacion = :nuevaAfiliacion WHERE afiliacion like :nombreAfiliacion")
    fun actualizarPersonajes(nuevaAfiliacion: String, nombreAfiliacion: String)

    @Insert
    fun addPersonaje(elemento: Personajes)

    @Delete
    fun deletePersonaje(elemento: Personajes)

    @Update
    fun updatePersonaje(elemento: Personajes)
}