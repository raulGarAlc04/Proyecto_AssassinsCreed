package com.example.proyecto_assassinscreed.database

import android.provider.ContactsContract.CommonDataKinds.Organization
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "afiliaciones")
data class Afiliaciones(
    @PrimaryKey
    var nombreAfiliacion:String = "",
    var lider:String = "",
    var guarida:String = "",
    var fechaFundacion:String = "",
    var descripcion:String = "",
    var organizacionCriminal:Boolean = false
)
