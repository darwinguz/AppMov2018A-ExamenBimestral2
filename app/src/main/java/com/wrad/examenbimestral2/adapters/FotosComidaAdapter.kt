package com.wrad.examenbimestral2.adapters

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.modelos.FotoComidaModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante
import kotlinx.android.synthetic.main.lista_fila_fotos_comida.view.*
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.*


class FotosComidaAdapter(private var items: ArrayList<FotoComidaModel>) :
        RecyclerView.Adapter<FotosComidaAdapter.ViewHolder>() {

    //FIXME no se puede acceder desde la instancia en FotosComidaActivity ??? why???
    fun swap(datas: ArrayList<FotoComidaModel>?) {
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
                .inflate(R.layout.lista_fila_fotos_comida, null, false)
        // set the view's size, margins, paddings and layout parameters
        //...
        return ViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        loadMapPreview(items[position].url!!) {
            holder.view.imagen_lista_fotos_comida.post {
                holder.view.imagen_lista_fotos_comida.setImageDrawable(it)
            }
        }
//        holder.view.imagen_lista_fotos_comida.setImageBitmap(items[position].imagen)
        holder.view.btn_eliminar_lista_fotos_comida.setOnClickListener {
            //TODO eliminar foto (check)
            deleteFile("${FirebaseAuth.getInstance().currentUser!!.uid}/${items[position].comidaId!!}/${items[position].nombre!!}") {
                DatabaseService.deleteByKey(items[position].id!!, Constante.getReferenceFotosComida(items[position].comidaId!!))
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    private fun deleteFile(urn: String, callback: () -> Unit) {
        // Create a storage reference from our app
        val storageRef = FirebaseStorage.getInstance().reference

        // Create a reference to the file to delete
        val desertRef = storageRef.child(urn)

        // Delete the file
        desertRef.delete()
                .addOnSuccessListener {
                    // File deleted successfully
                    Log.i("FotosComidaAdapter", "EXITO ELIMINANDO IMAGEN")
                    callback()
                }
                .addOnFailureListener {
                    // Uh-oh, an error occurred!
                    Log.i("FotosComidaAdapter", "ERROR ELIMINANDO IMAGEN" + it.toString())
                }
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size
}

fun loadMapPreview(url: String, ejecutar: (drawable: Drawable) -> Unit) {
    //start a background thread for networking
    Thread(Runnable {
        try {
            //download the drawable
            val drawable = Drawable.createFromStream(URL(url).content as InputStream, "src")
            //edit the view in the UI thread
            ejecutar(drawable)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }).start()
}

