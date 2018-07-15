package com.wrad.examenbimestral2.modelos

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class IngredienteModel(
        var id: String?,
        val nombreIngrediente: String?,
        val cantidad: Int?,
        val descripcionPreparacion: String?,
        val opcional: Boolean?,
        val tipoIngrediente: String?,
        val necesitaRefrigeracion: Boolean?,
        var comidaId: String?
) : Parcelable {
    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    )
}