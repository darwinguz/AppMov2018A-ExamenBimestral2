package com.wrad.examenbimestral2.actividades.vendedor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wrad.examenbimestral2.R
import kotlinx.android.synthetic.main.activity_vendedor.*

class VendedorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedor)

        btn_crear_comida_vendedor.setOnClickListener {
            goToActivity(CrearComidaActivity::class.java)
        }

        btn_editar_comida_vendedor.setOnClickListener {
            goToActivity(ListarComidasActivity::class.java)
        }

    }

    private fun <T> goToActivity(genericActivityClass: Class<T>) {
        val intent = Intent(this, genericActivityClass)
        startActivity(intent)
    }
}
