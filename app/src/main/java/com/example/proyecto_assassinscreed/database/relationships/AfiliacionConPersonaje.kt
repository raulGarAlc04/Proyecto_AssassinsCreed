package com.example.proyecto_assassinscreed.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.proyecto_assassinscreed.database.Afiliaciones
import com.example.proyecto_assassinscreed.database.Personajes

data class AfiliacionConPersonaje(
    @Embedded val afiliacion: Afiliaciones,
    @Relation(
        parentColumn = "nombreAfiliacion",
        entityColumn = "afiliacion"
    )
    val personajes: MutableList<Personajes>
)