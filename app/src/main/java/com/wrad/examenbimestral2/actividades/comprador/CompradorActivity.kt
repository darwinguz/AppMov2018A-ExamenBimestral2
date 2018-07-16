package com.wrad.examenbimestral2.actividades.comprador

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.modelos.ComidaModel
import kotlinx.android.synthetic.main.activity_comprador.*

class CompradorActivity : AppCompatActivity() {
    companion object {
        val carritoCompras = ArrayList<ComidaModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprador)

        btn_crear_ordenes_comprador.setOnClickListener {
            goToActivity(SeleccionarItemsActivity::class.java)
        }
    }

    private fun <T> goToActivity(genericActivityClass: Class<T>) {
        val intent = Intent(this, genericActivityClass)
        startActivity(intent)
    }
}
