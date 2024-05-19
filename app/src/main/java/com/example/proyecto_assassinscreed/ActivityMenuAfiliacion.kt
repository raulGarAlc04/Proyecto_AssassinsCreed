package com.example.proyecto_assassinscreed

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class ActivityMenuAfiliacion: AppCompatActivity() {
    companion object {
        var actividadActual = 0
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_afiliaciones, menu)
        for (i in 0 until menu.size()) {
            if (i == actividadActual) menu.getItem(i).isEnabled = false
            else menu.getItem(i).isEnabled = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.principal_afiliaciones -> {
                val intent = Intent(this, PrincipalAfliaciones::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 0
                startActivity(intent)
                true
            }
            R.id.annadir_afiliacion -> {
                val intent = Intent(this, AnnadirAfiliacion::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 1
                startActivity(intent)
                true
            }
            R.id.eliminar_afiliacion -> {
                val intent = Intent(this, EliminarAfiliacion::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 2
                startActivity(intent)
                true
            }
            R.id.actualizar_afiliacion -> {
                val intent = Intent(this, UpdateLider::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 3
                startActivity(intent)
                true
            }
            R.id.listar_afiliacion -> {
                val intent = Intent(this, PrincipalAfliaciones::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                actividadActual = 4
                startActivity(intent)
                true
            }
            R.id.volver_inicioAf -> {
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