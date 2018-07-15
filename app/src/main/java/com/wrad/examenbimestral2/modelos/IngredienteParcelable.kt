package com.wrad.examenbimestral2.modelos

import android.os.Parcel
import android.os.Parcelable

class IngredienteParcelable(
        var id: String?,
        val nombreIngrediente: String?,
        val cantidad: Int?,
        val descripcionPreparacion: String?,
        val opcional: Boolean?,
        val tipoIngrediente: String?,
        val necesitaRefrigeracion: Boolean?,
        var comidaId: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString())

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombreIngrediente)
        if (cantidad != null) {
            parcel.writeInt(cantidad)
        }
        parcel.writeString(descripcionPreparacion)
        if (opcional != null) {
            parcel.writeByte(if (opcional) 1 else 0)
        }
        parcel.writeString(tipoIngrediente)
        if (necesitaRefrigeracion != null) {
            parcel.writeByte(if (necesitaRefrigeracion) 1 else 0)
        }
        parcel.writeValue(comidaId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IngredienteParcelable> {
        override fun createFromParcel(parcel: Parcel): IngredienteParcelable {
            return IngredienteParcelable(parcel)
        }

        override fun newArray(size: Int): Array<IngredienteParcelable?> {
            return arrayOfNulls(size)
        }
    }

}



