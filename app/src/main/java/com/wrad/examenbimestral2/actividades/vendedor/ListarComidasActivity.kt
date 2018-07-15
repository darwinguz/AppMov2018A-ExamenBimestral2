package com.wrad.examenbimestral2.actividades.vendedor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.adapters.ComidaAdapter
import com.wrad.examenbimestral2.modelos.ComidaParcelable
import com.wrad.examenbimestral2.utilitarios.Constante
import java.util.*

class ListarComidasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val TAG = ListarComidasActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_comidas)

        selectAllRealTimeFirebase()
    }


    private fun selectAllRealTimeFirebase() {
        val databaseReferenceByModel = FirebaseDatabase.getInstance().getReference(Constante.COMIDA_FIREBASE)
        databaseReferenceByModel.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(TAG, databaseError.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
//                    dataSnapshot.getValue()
                    val datos: ArrayList<ComidaParcelable> = ArrayList()

                    for (it in dataSnapshot.children) {
                        val comida = it.getValue(ComidaParcelable::class.java)
                        datos.add(comida!!)
                        Log.i(TAG, comida.toString())
                        Log.i(TAG, it.toString())
                    }

                    //TODO enviar datos al recycler view
                    //recycler view
                    viewManager = LinearLayoutManager(this@ListarComidasActivity)
                    viewAdapter = ComidaAdapter(datos)

                    recyclerView = findViewById<RecyclerView>(R.id.recycler_view_comida).apply {
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
        })
    }

}
