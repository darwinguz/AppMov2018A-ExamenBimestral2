package com.wrad.examenbimestral2.actividades.delivery

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.adapters.OrdenesDeliveryAdapter
import com.wrad.examenbimestral2.modelos.OrdenModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante
import kotlinx.android.synthetic.main.activity_ver_ordenes_pendientes.*

class VerOrdenesPendientesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var items: ArrayList<OrdenModel>

    companion object {
        val ordenesAceptadas = ArrayList<OrdenModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_ordenes_pendientes)

        DatabaseService.selectAll("${Constante.ORDEN_FIREBASE}/BWGCMr4uXNNfOriir7P2HDrkkeB2", OrdenModel::class.java) {
            items = it as ArrayList<OrdenModel>
            var itemsFilter = items.filter {
                it.estado.equals(Constante.ESTADO_ORDEN_EN_ESPERA)
            }
            iniciarRecyclerView(itemsFilter)
        }
    }

    private fun iniciarRecyclerView(items: List<OrdenModel>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = OrdenesDeliveryAdapter(items as ArrayList<OrdenModel>)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view_ordenes_pend_delivery).apply {
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
