package com.wrad.examenbimestral2.modelos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class OrdenModel(
        var id: String,
        var usuario: UsuarioModel,
        var fecha: Date,
        var total: Double,
        var estado: Boolean,
        var ubicacionEntrega: String,
        var costoDelivery: Double,
        var fechaEntrega: Date,
        var costoEntrega: Double,
        var detallesOrden: List<DetalleOrdenModel>
) : Parcelable
