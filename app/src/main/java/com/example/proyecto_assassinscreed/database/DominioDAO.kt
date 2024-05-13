package com.example.proyecto_assassinscreed.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DominioDAO {
    @Query("SELECT * FROM dominio")
    fun getAllDominios(): MutableList<Dominio>

    @Query("SELECT * FROM dominio WHERE nombreDominio like :nombre")
    fun dominioPorNombre(nombre: String): MutableList<Dominio>

    @Insert
    fun addDominio(elemento: Dominio)

    @Delete
    fun deleteDominio(elemento: Dominio)

    @Update
    fun updateDominio(elemento: Dominio)
}