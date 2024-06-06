package com.example.proyecto_assassinscreed.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.proyecto_assassinscreed.database.Dominio
import com.example.proyecto_assassinscreed.database.Personajes

data class PersonajeConDominio(
    @Embedded val personaje: Personajes,
    @Relation(
        parentColumn = "nombrePersonaje",
        entityColumn = "liderDominio"
    )
    val dominios: MutableList<Dominio>
)