package com.wrad.examenbimestral2.actividades.comprador

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.adapters.ComidasEscogidasCompradorAdapter
import com.wrad.examenbimestral2.modelos.ComidaModel
import kotlinx.android.synthetic.main.activity_crear_orden.*
import java.util.*


class CrearOrdenActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var items: ArrayList<ComidaModel>

    companion object {
        private const val TAG = "CrearOrdenActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_orden)

        SeleccionarItemsActivity.carritoCompras.forEach {
            Log.i(TAG, it.toString())
        }

//        txt_total_crear_orden.setText(items.size)
        txt_total_crear_orden.isEnabled = false
        //txt_ubicacion_crear_orden.text
        txt_costo_delivery_crear_orden.setText("100")
        txt_fecha_entrega_crear_orden.setText(Date().toString())
        txt_costo_entrega_crear_orden.setText("200")

//        iniciarRecyclerView(items)
    }

    private fun iniciarRecyclerView(items: List<ComidaModel>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = ComidasEscogidasCompradorAdapter(items as ArrayList<ComidaModel>)

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
}
