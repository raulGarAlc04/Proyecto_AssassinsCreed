package com.example.proyecto_assassinscreed.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.proyecto_assassinscreed.database.Ciudades
import com.example.proyecto_assassinscreed.database.Personajes

data class PersonajeConCiudad(
    @Embedded val personaje: Personajes,
    @Relation(
        parentColumn = "nombrePersonaje",
        entityColumn = "gobernador"
    )
    val ciudades: MutableList<Ciudades>
)