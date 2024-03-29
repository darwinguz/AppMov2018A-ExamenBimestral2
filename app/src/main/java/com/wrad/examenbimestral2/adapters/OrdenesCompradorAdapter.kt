package com.wrad.examenbimestral2.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.modelos.OrdenModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante
import com.wrad.examenbimestral2.utilitarios.Notificacion
import kotlinx.android.synthetic.main.lista_fila_ordenes_comprador.view.*
import java.util.*


class OrdenesCompradorAdapter(private var items: ArrayList<OrdenModel>) :
        RecyclerView.Adapter<OrdenesCompradorAdapter.ViewHolder>() {

    //FIXME no se puede acceder desde la instancia en FotosComidaActivity ??? why???
    fun swap(datas: ArrayList<OrdenModel>?) {
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
                .inflate(R.layout.lista_fila_ordenes_comprador, null, false)
        // set the view's size, margins, paddings and layout parameters
        //...
        return ViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.view.lbl_estado_ordenes_comprador.text = items[position].estado
        holder.view.lbl_fecha_ordenes_comprador.text = items[position].fecha.toString()
        holder.view.lbl_costo_delivery_ordenes_comprador.text = items[position].costoDelivery.toString()
        holder.view.lbl_costo_entrega_ordenes_comprador.text = items[position].costoEntrega.toString()
        holder.view.lbl_total_ordenes_comprador.text = items[position].total.toString()
        holder.view.btn_cofirmar_ordenes_comprador.setOnClickListener {
            items[position].estado = Constante.ESTADO_ORDEN_EN_ESPERA
            notifyDataSetChanged()
            DatabaseService.updateSpecificValue("${Constante.ORDEN_FIREBASE}/${FirebaseAuth.getInstance().currentUser!!.uid}", items[position].id!!, "estado", Constante.ESTADO_ORDEN_EN_ESPERA)
            DatabaseService.updateSpecificValue("${Constante.ORDEN_FIREBASE}/${FirebaseAuth.getInstance().currentUser!!.uid}", items[position].id!!, "token", Notificacion.getTokenDevice().toString())
            Notificacion.sendNotification(Constante.TOKEN_DELIVERY, "Notificacion de Orden", "Tiene ordenes por confirmar...")
        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size
}



