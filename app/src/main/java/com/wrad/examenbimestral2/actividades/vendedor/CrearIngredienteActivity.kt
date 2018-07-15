package com.wrad.examenbimestral2.actividades.vendedor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.modelos.ComidaModel
import com.wrad.examenbimestral2.modelos.IngredienteModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante
import kotlinx.android.synthetic.main.activity_crear_ingrediente.*

class CrearIngredienteActivity : AppCompatActivity() {

    var comida: ComidaModel? = null
    var ingredienteEdit: IngredienteModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_ingrediente)

        comida = intent.getParcelableExtra("comida-intent")
        ingredienteEdit = intent.getParcelableExtra("ingrediente-edit-intent")

        if (ingredienteEdit != null) {
            txt_nombre_crear_ingrediente.setText(ingredienteEdit?.nombreIngrediente)
            txt_cantidad_crear_ingrediente.setText(ingredienteEdit?.cantidad.toString())
            txt_descripcion_crear_ingrediente.setText(ingredienteEdit?.descripcionPreparacion)
            txt_tipo_crear_ingrediente.setText(ingredienteEdit?.tipoIngrediente)
            chk_opcional_crear_ingrediente.isChecked = ingredienteEdit?.opcional!!
            chk_necesita_refrigeracion_crear_ingrediente.isChecked = ingredienteEdit?.necesitaRefrigeracion!!
        }

        btn_cancelar_crear_ingrediente.setOnClickListener {
            finish()
        }

        btn_guardar_crear_ingrediente.setOnClickListener {
            val ingrediente = IngredienteModel(
                    null,
                    txt_nombre_crear_ingrediente.text.toString(),
                    txt_cantidad_crear_ingrediente.text.toString().toInt(),
                    txt_descripcion_crear_ingrediente.text.toString(),
                    chk_opcional_crear_ingrediente.isChecked,
                    txt_tipo_crear_ingrediente.text.toString(),
                    chk_necesita_refrigeracion_crear_ingrediente.isChecked,
                    null
            )

            if (ingredienteEdit == null) {
                ingrediente.comidaId = comida?.id
                DatabaseService.insertWithAutogeratedKey(ingrediente, Constante.getReferenceIngredientes(comida?.id!!))
                DatabaseService.selectSingleByKey(ingrediente.comidaId!!, Constante.COMIDA_FIREBASE, ComidaModel::class.java, ::irIngredientes)
            } else {
                ingrediente.id = ingredienteEdit!!.id
                ingrediente.comidaId = ingredienteEdit!!.comidaId
                DatabaseService.updateAny(Constante.getReferenceIngredientes(ingredienteEdit!!.comidaId!!), ingrediente.id!!, ingrediente)
                DatabaseService.selectSingleByKey(ingredienteEdit!!.comidaId!!, Constante.COMIDA_FIREBASE, ComidaModel::class.java, ::irIngredientes)
            }
        }
    }

    private fun irIngredientes(comida: ComidaModel) {
        val intent = Intent(this, ListarIngredientesActivity::class.java)
        intent.putExtra("comida-intent", comida)
        startActivity(intent)
    }
}
