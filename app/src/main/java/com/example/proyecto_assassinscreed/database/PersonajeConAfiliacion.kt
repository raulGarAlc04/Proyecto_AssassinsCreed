package com.example.proyecto_assassinscreed.database

import androidx.room.Embedded
import androidx.room.Relation

data class PersonajeConAfiliacion(
    @Embedded val personaje: Personajes,
    @Relation(
        parentColumn = "nombrePersonaje",
        entityColumn = "lider"
    )
    val afiliacion: Afiliaciones
)
