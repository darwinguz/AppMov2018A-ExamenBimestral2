package com.wrad.examenbimestral2.actividades.delivery

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.utilitarios.Notificacion
import kotlinx.android.synthetic.main.activity_comprador.*
import kotlinx.android.synthetic.main.activity_delivery.*

class DeliveryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        btn_ver_ordenes_dlivery.setOnClickListener {
//            Notificacion.sendNotification("dOa9ITP8mrc:APA91bHkocnNgiMWY_dywGzB45SogwQXB1KH1IgvFnJIgpFBH67fgOjOthvzukVtB3DxQkDRkqf7uqHS-1bzGu1OBBlO7BfuPJlpc5v9mV93XpbdqbWQ4OoUZ9bsNR9r1yXc5KY4nTbUR5gpfs6oxLwDllMnopZGHg")
            Notificacion.sendNotificationAnko("dOa9ITP8mrc:APA91bHkocnNgiMWY_dywGzB45SogwQXB1KH1IgvFnJIgpFBH67fgOjOthvzukVtB3DxQkDRkqf7uqHS-1bzGu1OBBlO7BfuPJlpc5v9mV93XpbdqbWQ4OoUZ9bsNR9r1yXc5KY4nTbUR5gpfs6oxLwDllMnopZGHg")

//            goToActivity(VerOrdenesPendientesActivity::class.java)
        }
    }

    private fun <T> goToActivity(genericActivityClass: Class<T>) {
        val intent = Intent(this, genericActivityClass)
        startActivity(intent)
    }
}
