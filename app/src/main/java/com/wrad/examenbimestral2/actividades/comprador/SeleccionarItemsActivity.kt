package com.wrad.examenbimestral2.actividades.comprador

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.adapters.ComidasCompradorAdapter
import com.wrad.examenbimestral2.modelos.ComidaModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante
import kotlinx.android.synthetic.main.activity_seleccionar_items.*

class SeleccionarItemsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var items: ArrayList<ComidaModel>

    companion object {
        val carritoCompras = ArrayList<ComidaModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_items)

        inicializarRecyclerView()

        btn_aceptar_seleccionar_items.setOnClickListener {
            goToActivity(CrearOrdenActivity::class.java)
        }
    }

    private fun inicializarRecyclerView() {
        DatabaseService.selectAll(Constante.COMIDA_FIREBASE, ComidaModel::class.java) {
            items = it as ArrayList<ComidaModel>
            viewManager = LinearLayoutManager(this)
            viewAdapter = ComidasCompradorAdapter(items)

            recyclerView = findViewById<RecyclerView>(R.id.recycler_view_comidas_disponibles).apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView

//                setHasFixedSize(true)

                // use a linear layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter
            }
        }
    }

    private fun <T> goToActivity(genericActivityClass: Class<T>) {
        val intent = Intent(this, genericActivityClass)
        startActivity(intent)
    }

}
