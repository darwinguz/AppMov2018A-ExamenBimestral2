package com.wrad.examenbimestral2.modelos

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class OrdenParcelable(
        var id: String,
        var usuario: UsuarioParcelable,
        var fecha: Date,
        var total: Double,
        var estado: Boolean,
        var ubicacionEntrega: String,
        var costoDelivery: Double,
        var fechaEntrega: Date,
        var costoEntrega: Double,
        var detallesOrden: List<DetalleOrdenParcelable>
) : Parcelable
