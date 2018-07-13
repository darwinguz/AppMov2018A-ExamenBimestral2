package com.wrad.examenbimestral2.modelos

import android.os.Parcel
import android.os.Parcelable

class UsuarioParcelable(
        var id: String?,
        var username: String?,
        var password: String?,
        var tipo: String?,
        var comidas: List<ComidaParcelable>?,
        var ordenes: List<OrdenParcelable>?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(ComidaParcelable),
            parcel.createTypedArrayList(OrdenParcelable))

    constructor(username: String, password: String, tipo: String) : this(
            null,
            username,
            password,
            tipo,
            null,
            null)

    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null)

    constructor(id: String) : this(
            id,
            null,
            null,
            null,
            null,
            null)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(tipo)
        parcel.writeTypedList(comidas)
        parcel.writeTypedList(ordenes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UsuarioParcelable> {
        override fun createFromParcel(parcel: Parcel): UsuarioParcelable {
            return UsuarioParcelable(parcel)
        }

        override fun newArray(size: Int): Array<UsuarioParcelable?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "id: $id, username: $username, password: $password, tipo: $tipo, comidas: $comidas,  ordenes: $ordenes"
    }
}
