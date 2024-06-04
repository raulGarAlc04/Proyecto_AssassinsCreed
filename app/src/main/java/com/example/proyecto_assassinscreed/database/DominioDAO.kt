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

    @Query("SELECT nombreDominio FROM dominio")
    fun nombresDiminios(): MutableList<String>

    @Query("SELECT * FROM dominio WHERE liderDominio like :nombreLider")
    fun filtrarPorLiderDominio(nombreLider: String): MutableList<Dominio>

    @Insert
    fun addDominio(elemento: Dominio)

    @Delete
    fun deleteDominio(elemento: Dominio)

    @Query("DELETE FROM dominio WHERE liderDominio like :nombreLiderDominio")
    fun deletePorLiderDominio(nombreLiderDominio: String)

    @Query("UPDATE dominio SET liderDominio = :nombreLiderDominioAct WHERE nombreDominio like :dominio")
    fun updateDominio(nombreLiderDominioAct: String, dominio: String)

    @Update
    fun updateDominio(elemento: Dominio)
}