package com.example.proyecto_assassinscreed.database

import androidx.room.Embedded
import androidx.room.Relation

data class PersonajeConCiudad(
    @Embedded val personaje: Personajes,
    @Relation(
        parentColumn = "nombrePersonaje",
        entityColumn = "gobernador"
    )
    val ciudad: Ciudades
)
