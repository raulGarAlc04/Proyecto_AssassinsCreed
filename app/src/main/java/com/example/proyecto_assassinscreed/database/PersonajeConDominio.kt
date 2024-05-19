package com.example.proyecto_assassinscreed.database

import androidx.room.Embedded
import androidx.room.Relation

data class PersonajeConDominio(
    @Embedded val personaje: Personajes,
    @Relation(
        parentColumn = "nombrePersonaje",
        entityColumn = "liderDominio"
    )
    val dominio: Dominio
)
