package com.wrad.examenbimestral2.actividades.comprador

import android.app.Service
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.adapters.OrdenesCompradorAdapter
import com.wrad.examenbimestral2.modelos.OrdenModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante

class VerOrdenesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var items: ArrayList<OrdenModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_ordenes)

        DatabaseService.selectAll("${Constante.ORDEN_FIREBASE}/${FirebaseAuth.getInstance().currentUser!!.uid}", OrdenModel::class.java) {
            iniciarRecyclerView(it)
        }
    }

    private fun iniciarRecyclerView(items: List<OrdenModel>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = OrdenesCompradorAdapter(items as ArrayList<OrdenModel>)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview_ver_ordenes_comprador).apply {
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
