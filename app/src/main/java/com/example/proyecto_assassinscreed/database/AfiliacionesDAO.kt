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

    @Query("SELECT nombreAfiliacion FROM afiliaciones")
    fun nombresAfiliaciones(): MutableList<String>

    @Query("SELECT * FROM afiliaciones WHERE lider like :nombreLider")
    fun filtrarPorLider(nombreLider: String): MutableList<Afiliaciones>

    @Query("SELECT * FROM Afiliaciones WHERE lider = :lider")
    fun afiliacionPorLider(lider: String): Afiliaciones

    @Query("UPDATE Afiliaciones SET lider = :nuevoLider WHERE nombreAfiliacion = :nombreAfiliacion")
    fun actualizarLiderDeAfiliacion(nombreAfiliacion: String, nuevoLider: String)

    @Insert
    fun addAfiliacion(elemento: Afiliaciones)

    @Delete
    fun deleteAfiliacion(elemento: Afiliaciones)

    @Query("DELETE FROM afiliaciones WHERE lider like :nombreLider")
    fun deletePorLider(nombreLider: String)

    @Update
    fun updateAfiliacion(elemento: Afiliaciones)
}