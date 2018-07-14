package com.wrad.examenbimestral2.modelos

import android.os.Parcel
import android.os.Parcelable

class ComidaParcelable(val id: String?,
                       val nombrePlato: String?,
                       val descripcionPlato: String?,
                       val nacionalidad: String?,
                       val numeroPersonas: Int?,
                       val picante: Boolean?,
                       val ingredientes: List<IngredienteParcelable>?,
                       val usuario: UsuarioParcelable?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.createTypedArrayList(IngredienteParcelable),
            parcel.readParcelable(UsuarioParcelable::class.java.classLoader))

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
        parcel.writeString(nombrePlato)
        parcel.writeString(descripcionPlato)
        parcel.writeString(nacionalidad)
        parcel.writeInt(numeroPersonas!!)
        parcel.writeByte(if (picante!!) 1 else 0)
        parcel.writeTypedList(ingredientes)
        parcel.writeParcelable(usuario, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComidaParcelable> {
        override fun createFromParcel(parcel: Parcel): ComidaParcelable {
            return ComidaParcelable(parcel)
        }

        override fun newArray(size: Int): Array<ComidaParcelable?> {
            return arrayOfNulls(size)
        }
    }


}