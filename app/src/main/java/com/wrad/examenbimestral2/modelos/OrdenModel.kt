package com.wrad.examenbimestral2.modelos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class OrdenModel(
        var id: String?,
        var usuario: UsuarioModel?,
        var fecha: Date?,
        var total: Double?,
        var estado: String?,
        var ubicacionEntrega: String?,
        var costoDelivery: Double?,
        var fechaEntrega: Date?,
        var costoEntrega: Double?,
        var detallesOrden: List<DetalleOrdenModel>?,
        var token: String?
) : Parcelable {
    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    )

    constructor(
            usuario: UsuarioModel,
            fecha: Date,
            total: Double,
            estado: String,
            ubicacionEntrega: String,
            costoDelivery: Double,
            fechaEntrega: Date,
            costoEntrega: Double,
            detallesOrden: List<DetalleOrdenModel>
    ) : this(null, usuario, fecha, total, estado, ubicacionEntrega, costoDelivery, fechaEntrega, costoEntrega, detallesOrden, null)
}

