package com.example.proyecto_assassinscreed.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.proyecto_assassinscreed.database.Ciudades
import com.example.proyecto_assassinscreed.database.Dominio

data class DominioConCiudad(
    @Embedded val dominio: Dominio,
    @Relation(
        parentColumn = "nombreDominio",
        entityColumn = "dominio"
    )
    val ciudades: MutableList<Ciudades>
)