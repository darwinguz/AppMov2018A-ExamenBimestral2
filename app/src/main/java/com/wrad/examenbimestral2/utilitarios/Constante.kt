package com.wrad.examenbimestral2.utilitarios

object Constante {
    const val ROL_COMPRADOR = "Comprador"
    const val ROL_VENDEDOR = "Vendedor"
    const val ROL_DELIVERY = "Delivery"

    const val USUARIO_FIREBASE = "usuario"
    const val COMIDA_FIREBASE = "comida"
    const val INGREDIENTE_FIREBASE = "ingredientes"

    fun getReferenceIngredientes(keyComida: String): String {
        return "$COMIDA_FIREBASE/$keyComida/$INGREDIENTE_FIREBASE"
    }

}