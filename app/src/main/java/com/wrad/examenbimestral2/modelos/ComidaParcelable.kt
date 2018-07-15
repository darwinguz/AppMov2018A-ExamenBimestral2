package com.wrad.examenbimestral2.modelos

import android.os.Parcel
import android.os.Parcelable

class ComidaParcelable(var id: String?,
                       var nombrePlato: String?,
                       var descripcionPlato: String?,
                       var nacionalidad: String?,
                       var numeroPersonas: Int?,
                       var picante: Boolean?,
                       var ingredientes: List<IngredienteParcelable>?,
                       var usuario: UsuarioParcelable?
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
        if (numeroPersonas != null) {
            parcel.writeInt(numeroPersonas!!)
        }
        parcel.writeByte(if (picante == null) 0 else {
            (if (picante as Boolean) 1 else 0).toByte()
        })
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

    override fun toString(): String {
        return "id: $id, nombrePlato: $nombrePlato, descripcionPlato: $descripcionPlato, nacionalidad: $nacionalidad, numeroPersonas: $numeroPersonas, picante: $picante, ingredientes: $ingredientes, usuario: $usuario"
    }
}