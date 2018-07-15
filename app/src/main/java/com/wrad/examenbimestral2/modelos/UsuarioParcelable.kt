package com.wrad.examenbimestral2.modelos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UsuarioParcelable(
        var id: String?,
        var username: String?,
        var password: String?,
        var tipo: String?,
        var comidas: List<ComidaParcelable>?,
        var ordenes: List<OrdenParcelable>?) : Parcelable {

    constructor(username: String, password: String, tipo: String) : this(
            null,
            username,
            password,
            tipo,
            null,
            null
    )

    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null
    )

    constructor(id: String) : this(
            id,
            null,
            null,
            null,
            null,
            null
    )

    override fun toString(): String {
        return "id: $id, username: $username, password: $password, tipo: $tipo, comidas: $comidas,  ordenes: $ordenes"
    }
}
