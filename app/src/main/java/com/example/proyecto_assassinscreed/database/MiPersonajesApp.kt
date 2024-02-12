package com.example.proyecto_assassinscreed.database

import android.app.Application
import androidx.room.Room

class MiPersonajesApp : Application() {
    companion object {
        lateinit var database: DBPersonajes
    }

    override fun onCreate() {
        super.onCreate()
        MiPersonajesApp.database = Room.databaseBuilder(this, DBPersonajes::class.java,"DBPersonajes").build()
    }
}