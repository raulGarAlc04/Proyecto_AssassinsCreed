package com.example.proyecto_assassinscreed

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get

open class ActivityWithMenus: AppCompatActivity() {
    companion object {
        var actividadActual = 0
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.menu_personajes, menu)
        for (i in 0 until menu.size()) {
            if (i == actividadActual) menu.getItem(i).isEnabled = false
            else menu.getItem(i).isEnabled = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.inicio -> {
                val intent = Intent(this, InicioActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 0
                startActivity(intent)
                true
            }
            R.id.annadir_personaje -> {
                val intent = Intent(this, AnnadirPersonaje::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 1
                startActivity(intent)
                true
            }
            R.id.eliminar_personaje -> {
                val intent = Intent(this, EliminarActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 2
                startActivity(intent)
                true
            }
            R.id.actualizar_personaje -> {
                val intent = Intent(this, UpdateAfiliacion::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 3
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}