package com.example.proyecto_assassinscreed.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CiudadesDAO {
    @Query("SELECT * FROM ciudades")
    fun getAllCiudades(): MutableList<Ciudades>

    @Query("SELECT * FROM ciudades WHERE ciudad like :nombre")
    fun ciudadPorNombre(nombre: String): MutableList<Ciudades>

    @Query("SELECT ciudad FROM ciudades")
    fun nombresCiudades(): MutableList<String>

    @Insert
    fun addCiudad(elemento: Ciudades)

    @Delete
    fun deleteCiudad(elemento: Ciudades)

    @Update
    fun updateCiudad(elemento: Ciudades)
}