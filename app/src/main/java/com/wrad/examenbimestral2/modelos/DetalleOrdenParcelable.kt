package com.wrad.examenbimestral2.modelos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetalleOrdenParcelable(
        var id: String,
        var comida: ComidaParcelable?,
        var precio: Double
) : Parcelable