package com.example.proyecto_assassinscreed

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class ActivityMenuCiudades: AppCompatActivity() {
    companion object {
        var actividadActual = 0
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_ciudad, menu)
        for (i in 0 until menu.size()) {
            if (i == actividadActual) menu.getItem(i).isEnabled = false
            else menu.getItem(i).isEnabled = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.principal_ciudad -> {
                val intent = Intent(this, PrincipalCiudades::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 0
                startActivity(intent)
                true
            }
            R.id.annadir_ciudad -> {
                val intent = Intent(this, AnnadirCiudad::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 1
                startActivity(intent)
                true
            }
            R.id.eliminar_ciudad -> {
                val intent = Intent(this, EliminarCiudad::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 2
                startActivity(intent)
                true
            }
            R.id.actualizar_ciudad -> {
                val intent = Intent(this, UpdateCiudad::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 3
                startActivity(intent)
                true
            }
            R.id.listar_ciudades -> {
                val intent = Intent(this, PrincipalCiudades::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 4
                startActivity(intent)
                true
            }
            R.id.volver_inicioCi -> {
                val intent = Intent(this, InicioActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 5
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}