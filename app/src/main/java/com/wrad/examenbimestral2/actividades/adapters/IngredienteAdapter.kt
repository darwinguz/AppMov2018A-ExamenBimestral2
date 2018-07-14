package com.wrad.examenbimestral2.actividades.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.actividades.vendedor.CrearIngredienteActivity
import com.wrad.examenbimestral2.actividades.vendedor.ListarIngredientesActivity
import com.wrad.examenbimestral2.modelos.ComidaParcelable
import com.wrad.examenbimestral2.modelos.IngredienteParcelable
import kotlinx.android.synthetic.main.lista_fila_ingrediente.view.*
import java.util.*

class IngredienteAdapter(private val ingredientes: ArrayList<IngredienteParcelable>) :
        RecyclerView.Adapter<IngredienteAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        init {
            view.lbl_nombre_lista_ingrediente.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            //menu!!.setHeaderTitle("Select The Action");
            val editar = menu!!.add("Editar")
            val eliminar = menu.add("Eliminar")
            val compartirCorreo = menu.add("Compartir por Correo")

            //FIXME ARREGLAR EL LLAMADO DEL OBJETO SELECCIONADO EN EL CONTEXT VIEW Y EL REFRESH
            editar.setOnMenuItemClickListener {
                //TODO implementar servicio
//                val serIngrediente = SerIngrediente(view.context)
//                val ingredienteEditar = serIngrediente.selectByName(view.lbl_nombre_lista_ingrediente.text.toString())
//                if (ingredienteEditar != null) {
//                    irCrearIngrediente(view.context, ingredienteEditar)
//                }
                true
            }

            //FIXME ARREGLAR EL LLAMADO DEL OBJETO SELECCIONADO EN EL CONTEXT VIEW Y EL REFRESH
            eliminar.setOnMenuItemClickListener {
                val builder = AlertDialog.Builder(view.context)
                builder.setMessage("¿Desea eliminar esta comida?")
                        .setPositiveButton("Confirmar", { _, _ ->
                            //TODO implementar servicio
//                            val serIngrediente = SerIngrediente(view.context)
//                            val ingredienteEliminar = serIngrediente.selectByName(view.lbl_nombre_lista_ingrediente.text.toString())
//                            if (ingredienteEliminar != null) {
//                                serIngrediente.delete(ingredienteEliminar.id!!)
//                            }
//                            irListarIngredientes(SerComida(view.context).selectById(ingredienteEliminar!!.comidaId!!)!!)
                        })
                        .setNegativeButton("Cancelar", null)
                val dialogo = builder.create()
                dialogo.show()
                true
            }

            compartirCorreo.setOnMenuItemClickListener {
                //TODO implementar servicio
//                val serIngrediente = SerIngrediente(view.context)
//                val ingredienteEnviar = serIngrediente.selectByName(view.lbl_nombre_lista_ingrediente.text.toString())
//                if (ingredienteEnviar != null) {
//                    enviarCorreo(ingredienteEnviar)
//                }
                true
            }
        }

        private fun enviarCorreo(ingrediente: IngredienteParcelable) {
            val addressees = arrayOf("direccion@uno.com", "direccion@dos.com")
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_EMAIL, addressees)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Ingrediente - Examen Bimestral")
            intent.putExtra(Intent.EXTRA_TEXT, "Ingrediente: ID=${ingrediente.id} Nombre=${ingrediente.nombreIngrediente} Cantidad=${ingrediente.cantidad} DescripcionPreparacion=${ingrediente.descripcionPreparacion} Opcional=${ingrediente.opcional} Tipo=${ingrediente.tipoIngrediente} NecesitaRefrigeracion=${ingrediente.necesitaRefrigeracion} ComidaID=${ingrediente.comidaId}")

            ContextCompat.startActivity(view.context, intent, null)
        }


        private fun irListarIngredientes(comida: ComidaParcelable) {
            val intent = Intent(view.context, ListarIngredientesActivity::class.java)
            intent.putExtra("comida-intent", comida)
            ContextCompat.startActivity(view.context, intent, null)
        }

        private fun irCrearIngrediente(context: Context, ingredienteSelected: IngredienteParcelable) {
            val intent = Intent(context, CrearIngredienteActivity::class.java)
            intent.putExtra("ingrediente-edit-intent", ingredienteSelected)
            Log.e("info", "INGREDIENTE POR EDITAR ENVIADA: $ingredienteSelected ")
            ContextCompat.startActivity(context, intent, null)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)//root=null para que se ajuste la pantalla
                .inflate(R.layout.lista_fila_ingrediente, null, false)
        // set the view's size, margins, paddings and layout parameters
        //...
        return ViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.view.lbl_nombre_lista_ingrediente.text = ingredientes[position].nombreIngrediente
        holder.view.lbl_cantidad_lista_ingrediente.text = ingredientes[position].cantidad.toString()
        holder.view.lbl_descripcion_lista_ingrediente.text = ingredientes[position].descripcionPreparacion
        holder.view.lbl_opcional_lista_ingrediente.text = if (ingredientes[position].opcional) "Opcional" else "No Opcional"
        holder.view.lbl_tipo_lista_ingrediente.text = ingredientes[position].tipoIngrediente
        holder.view.lbl_necesita_refrigeracion_lista_ingrediente.text = if (ingredientes[position].necesitaRefrigeracion) "Necesita Refrigeración" else "No Necesita Refrigeración"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = ingredientes.size
}