package com.wrad.examenbimestral2.utilitarios

object Constante {
    const val ROL_COMPRADOR = "Comprador"
    const val ROL_VENDEDOR = "Vendedor"
    const val ROL_DELIVERY = "Delivery"

    const val USUARIO_FIREBASE = "usuario"
    const val COMIDA_FIREBASE = "comida"
    const val INGREDIENTE_FIREBASE = "ingredientes"
    const val FOTOS_COMIDA_FIREBASE = "fotos"
    const val ORDEN_FIREBASE = "orden"
    const val LEGACY_SERVER_KEY = "AAAAWHsymYI:APA91bGQbmclOv1R6crGXnxITZT3vDU0tJv3DlgX1TgFj0RcZesSh460StPhFrHi1wZqUe51vpBLxmgEtXxhpmZm3K-uhPu6bS9N2TasiKy84RSFZ70Be-eCo7lTNCg1Lg5QnDXuBr2zDuxa2n46OXWV9Wq1KDxddA"
    const val TOKEN_DELIVERY = "fYsEvxA0sUg:APA91bGP6fpNCLYq7pG_veJPLx--Q1Jd61tq9Qm2-E17oMQL7nH1EalAOyF6QEue_wcNCBgK1kXRhITNO72dXO8A5U6ZKGISxmUbSjZzaYIJ1vLDctLHM9Atbz2hZMYOwUcgon8WNx1YVqDwCyR8jgrYxzNUSv1_5Q"
    fun getReferenceIngredientes(keyComida: String): String {
        return "$COMIDA_FIREBASE/$keyComida/$INGREDIENTE_FIREBASE"
    }

    fun getReferenceFotosComida(keyComida: String): String {
        return "$COMIDA_FIREBASE/$keyComida/$FOTOS_COMIDA_FIREBASE"
    }

    const val ESTADO_ORDEN_RESERVADO = "Reservado"
    const val ESTADO_ORDEN_EN_ESPERA = "En orden"
    const val ESTADO_ORDEN_ACEPTADO = "Aceptado"
//    const val ESTADO_ORDEN_ENTREGADO = "Entregado"

}