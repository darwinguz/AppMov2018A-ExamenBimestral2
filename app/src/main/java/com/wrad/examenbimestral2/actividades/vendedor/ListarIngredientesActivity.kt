package com.wrad.examenbimestral2.actividades.vendedor


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.adapters.IngredienteAdapter
import com.wrad.examenbimestral2.modelos.ComidaModel
import com.wrad.examenbimestral2.modelos.IngredienteModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante
import kotlinx.android.synthetic.main.activity_listar_ingredientes.*
import java.util.*

class ListarIngredientesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    var comida: ComidaModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_ingredientes)

        comida = intent.getParcelableExtra("comida-intent")
        Log.i("info", "COMIDA RECIBIDA: $comida ")

        if (comida != null) {
            txt_nombre_ingrediente.text = comida?.nombrePlato
            txt_descripcion_ingrediente.text = comida?.descripcionPlato
            txt_nacionalidad_ingrediente.text = comida?.nacionalidad
            txt_numero_personas_ingrediente.text = comida?.numeroPersonas.toString()
            chk_picante_ingrediente.isChecked = comida?.picante!!

            DatabaseService.selectAll(Constante.getReferenceIngredientes(comida!!.id!!), IngredienteModel::class.java, ::iniciarRecyclerView)
        } else {
            throw Exception("No se ha enviado ninguna comida a esta actividad.")
        }

        btn_nuevo_ingrediente.setOnClickListener {
            irCrearIngrediente()
        }

    }

    private fun iniciarRecyclerView(ingredientes: List<IngredienteModel>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = IngredienteAdapter(ingredientes as ArrayList<IngredienteModel>)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view_ingrediente).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    private fun irCrearIngrediente() {
        val intent = Intent(this, CrearIngredienteActivity::class.java)
        intent.putExtra("comida-intent", comida)
        startActivity(intent)
    }
}
