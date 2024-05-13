package com.example.proyecto_assassinscreed.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsuariosDAO {
    @Query("SELECT * FROM usuarios")
    fun getAllUsuarios(): MutableList<Usuarios>

    @Query("SELECT * FROM usuarios WHERE nombreUsuario like :nombre")
    fun usuarioPorNombre(nombre: String): MutableList<Usuarios>

    @Insert
    fun addUsuarios(elemento: Usuarios)

    @Delete
    fun deleteUsuarios(elemento: Usuarios)

    @Update
    fun updateUsuarios(elemento: Usuarios)
}