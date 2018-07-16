package com.wrad.examenbimestral2.actividades.vendedor


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.wrad.examenbimestral2.MainActivity
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.modelos.ComidaModel
import com.wrad.examenbimestral2.modelos.UsuarioModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante
import kotlinx.android.synthetic.main.activity_crear_comida.*

class CrearComidaActivity : AppCompatActivity() {
    var comidaToEdit: ComidaModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_comida)

        comidaToEdit = intent.getParcelableExtra("comida-edit-intent")
        Log.i("info", "COMIDA RECIBIDA POR EDITAR: $comidaToEdit ")

        if (comidaToEdit != null) {
            lbl_crear_comida_title.text = "Editar Comida"

            txt_nombre_comida.setText(comidaToEdit?.nombrePlato)
            txt_descripcion_comida.setText(comidaToEdit?.descripcionPlato)
            txt_nacionalidad_comida.setText(comidaToEdit?.nacionalidad)
            txt_numero_personas_comida.setText(comidaToEdit?.numeroPersonas.toString())
            chk_picante_comida.isChecked = comidaToEdit?.picante!!
        }

        btn_guardar_comida.setOnClickListener {
            guardarComida()
            irListarComida()
        }

        btn_cancelar_comida.setOnClickListener {
            irMenuPrincipal()
        }
    }

    private fun guardarComida() {
        if (comidaToEdit == null) {
            val comida = ComidaModel(
                    txt_nombre_comida.text.toString(),
                    txt_descripcion_comida.text.toString(),
                    txt_nacionalidad_comida.text.toString(),
                    txt_numero_personas_comida.text.toString().toInt(),
                    chk_picante_comida.isChecked,
                    UsuarioModel(FirebaseAuth.getInstance().currentUser!!.uid))
            DatabaseService.insertWithAutogeratedKey(comida, Constante.COMIDA_FIREBASE)
        } else {
            val comidaUpdated = ComidaModel(
                    comidaToEdit!!.id!!,
                    txt_nombre_comida.text.toString(),
                    txt_descripcion_comida.text.toString(),
                    txt_nacionalidad_comida.text.toString(),
                    txt_numero_personas_comida.text.toString().toInt(),
                    chk_picante_comida.isChecked,
                    comidaToEdit!!.ingredientes,
                    UsuarioModel(FirebaseAuth.getInstance().currentUser!!.uid),
                    comidaToEdit!!.fotos
            )
            DatabaseService.updateAny(Constante.COMIDA_FIREBASE, comidaToEdit!!.id!!, comidaUpdated)
        }
    }

    private fun irListarComida() {
        val intent = Intent(this, ListarComidasActivity::class.java)
        startActivity(intent)
    }

    private fun irMenuPrincipal() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}
