package com.wrad.examenbimestral2.modelos

import android.os.Parcel
import android.os.Parcelable

class IngredienteParcelable( var id: String?,
                             val nombreIngrediente: String,
                             val cantidad: Int,
                             val descripcionPreparacion: String,
                             val opcional: Boolean,
                             val tipoIngrediente: String,
                             val necesitaRefrigeracion: Boolean,
                             var comidaId: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombreIngrediente)
        parcel.writeInt(cantidad)
        parcel.writeString(descripcionPreparacion)
        parcel.writeByte(if (opcional) 1 else 0)
        parcel.writeString(tipoIngrediente)
        parcel.writeByte(if (necesitaRefrigeracion) 1 else 0)
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



