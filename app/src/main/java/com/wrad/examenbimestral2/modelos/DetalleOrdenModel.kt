package com.wrad.examenbimestral2.modelos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetalleOrdenModel(
        val id: String?,
        val comida: ComidaModel?,
        val precio: Double?
) : Parcelable {

    constructor(comida: ComidaModel, precio: Double)
            : this(null, comida, precio)
}