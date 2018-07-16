package com.wrad.examenbimestral2.utilitarios

import com.wrad.examenbimestral2.modelos.ComidaModel

object Constante {
    const val ROL_COMPRADOR = "Comprador"
    const val ROL_VENDEDOR = "Vendedor"
    const val ROL_DELIVERY = "Delivery"

    const val USUARIO_FIREBASE = "usuario"
    const val COMIDA_FIREBASE = "comida"
    const val INGREDIENTE_FIREBASE = "ingredientes"
    const val FOTOS_COMIDA_FIREBASE = "fotos"

    fun getReferenceIngredientes(keyComida: String): String {
        return "$COMIDA_FIREBASE/$keyComida/$INGREDIENTE_FIREBASE"
    }

    fun getReferenceFotosComida(keyComida: String): String {
        return "$COMIDA_FIREBASE/$keyComida/$FOTOS_COMIDA_FIREBASE"
    }

}