package com.wrad.examenbimestral2.utilitarios

import android.content.Context
import android.widget.Toast


object Mensaje {
    fun emitirInformacion(context: Context, contenido: String) {
        Toast.makeText(context, contenido, Toast.LENGTH_LONG).show()
    }

    fun emitirError(context: Context, contenido: String) {
        Toast.makeText(context, contenido, Toast.LENGTH_LONG).show()
    }
}