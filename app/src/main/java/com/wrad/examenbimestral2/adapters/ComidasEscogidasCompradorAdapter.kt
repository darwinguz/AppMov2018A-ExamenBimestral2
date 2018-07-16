package com.wrad.examenbimestral2.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.modelos.ComidaModel
import kotlinx.android.synthetic.main.lista_fila_comidas_escogidas.view.*
import java.util.*


class ComidasEscogidasCompradorAdapter(private var items: ArrayList<ComidaModel>) :
        RecyclerView.Adapter<ComidasEscogidasCompradorAdapter.ViewHolder>() {

    //FIXME no se puede acceder desde la instancia en FotosComidaActivity ??? why???
    fun swap(datas: ArrayList<ComidaModel>?) {
        if (datas == null || datas.size == 0)
            return
        if (items.size > 0)
            items.clear()
        items.addAll(datas)
        notifyDataSetChanged()
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)//root=null para que se ajuste la pantalla
                .inflate(R.layout.lista_fila_comidas_escogidas, null, false)
        // set the view's size, margins, paddings and layout parameters
        //...
        return ViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.view.lbl_nombre_lista_comida_escogida.text = items[position].nombrePlato

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size
}



