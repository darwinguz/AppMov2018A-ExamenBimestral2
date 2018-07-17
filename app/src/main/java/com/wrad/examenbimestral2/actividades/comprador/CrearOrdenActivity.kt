package com.wrad.examenbimestral2.actividades.comprador

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.adapters.DetallesOrdenAdapter
import com.wrad.examenbimestral2.modelos.DetalleOrdenModel
import com.wrad.examenbimestral2.modelos.OrdenModel
import com.wrad.examenbimestral2.modelos.UsuarioModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante
import com.wrad.examenbimestral2.utilitarios.Mensaje
import kotlinx.android.synthetic.main.activity_crear_orden.*
import java.util.*
import kotlin.collections.ArrayList


class CrearOrdenActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var detallesOrden: ArrayList<DetalleOrdenModel>


    companion object {
        private const val TAG = "CrearOrdenActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_orden)

        val carritoCompras = SeleccionarItemsActivity.carritoCompras

        detallesOrden = ArrayList()
        SeleccionarItemsActivity.carritoCompras.forEach {
            Log.i(TAG, it.toString())
            detallesOrden.add(DetalleOrdenModel(it, 1.00))
        }

        txt_total_comidas_crear_orden.setText(carritoCompras.size.toString())
        txt_total_comidas_crear_orden.isEnabled = false
        //txt_ubicacion_crear_orden.text
        txt_costo_delivery_crear_orden.setText("100")
        txt_costo_delivery_crear_orden.isEnabled = false
        txt_fecha_entrega_crear_orden.setText(Date().toString())
        txt_fecha_entrega_crear_orden.isEnabled = false
        txt_costo_entrega_crear_orden.setText("200")
        txt_costo_entrega_crear_orden.isEnabled = false

        iniciarRecyclerView(detallesOrden)

        btn_crear_orden.setOnClickListener {
            val orden = OrdenModel(
                    UsuarioModel(FirebaseAuth.getInstance().currentUser!!.uid),
                    Date(),
                    txt_total_comidas_crear_orden.text.toString().toDouble(),
                    Constante.ESTADO_ORDEN_RESERVADO,
                    txt_ubicacion_crear_orden.text.toString(),
                    txt_costo_delivery_crear_orden.text.toString().toDouble(),
                    Date(),
                    txt_costo_entrega_crear_orden.text.toString().toDouble(),
                    this.detallesOrden
            )
            DatabaseService.insertWithAutogeratedKey(orden, "${Constante.ORDEN_FIREBASE}/${orden.usuario!!.id}") {
                finish()
                goToActivity(CompradorActivity::class.java)
                Mensaje.emitirInformacion(this, "Orden creada cone exito.")
            }
        }
    }

    private fun iniciarRecyclerView(items: List<DetalleOrdenModel>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = DetallesOrdenAdapter(items as ArrayList<DetalleOrdenModel>)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view_escogidos_comprador).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    private fun <T> goToActivity(genericActivityClass: Class<T>) {
        val intent = Intent(this, genericActivityClass)
        startActivity(intent)
    }
}
