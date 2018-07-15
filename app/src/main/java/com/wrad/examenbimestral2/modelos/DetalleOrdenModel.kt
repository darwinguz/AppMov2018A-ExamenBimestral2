package com.wrad.examenbimestral2.modelos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetalleOrdenModel(
        var id: String,
        var comida: ComidaModel?,
        var precio: Double
) : Parcelable