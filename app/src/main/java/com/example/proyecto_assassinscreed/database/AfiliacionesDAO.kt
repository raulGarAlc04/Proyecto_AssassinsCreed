package com.example.proyecto_assassinscreed.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AfiliacionesDAO {
    @Query("SELECT * FROM afiliaciones")
    fun getAllAfiliaciones(): MutableList<Afiliaciones>

    @Query("SELECT * FROM afiliaciones WHERE nombreAfiliacion like :nombre")
    fun afiliacionPorNombre(nombre: String): MutableList<Afiliaciones>

    @Insert
    fun addAfiliacion(elemento: Afiliaciones)

    @Delete
    fun deleteAfiliacion(elemento: Afiliaciones)

    @Query("DELETE FROM afiliaciones WHERE lider like :nombreLider")
    fun deletePorLider(nombreLider: String)

    @Query("UPDATE afiliaciones SET lider = :nombreLiderAct WHERE nombreAfiliacion like :nombreAfiliacion")
    fun updateAfiliacion(nombreLiderAct: String, nombreAfiliacion: String)

    @Update
    fun updateAfiliacion(elemento: Afiliaciones)
}