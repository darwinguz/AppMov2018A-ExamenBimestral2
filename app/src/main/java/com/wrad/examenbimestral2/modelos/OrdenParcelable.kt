package com.wrad.examenbimestral2.modelos

import android.os.Parcel
import android.os.Parcelable
import java.util.*

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(UsuarioParcelable::class.java.classLoader),
            parcel.leerDate()!!,
            parcel.readDouble(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.leerDate()!!,
            parcel.readDouble(),
            parcel.createTypedArrayList(DetalleOrdenParcelable))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(usuario, flags)
        parcel.escribirDate(fecha)
        parcel.writeDouble(total)
        parcel.writeByte(if (estado) 1 else 0)
        parcel.writeString(ubicacionEntrega)
        parcel.writeDouble(costoDelivery)
        parcel.escribirDate(fechaEntrega)
        parcel.writeDouble(costoEntrega)
        parcel.writeTypedList(detallesOrden)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrdenParcelable> {
        override fun createFromParcel(parcel: Parcel): OrdenParcelable {
            return OrdenParcelable(parcel)
        }

        override fun newArray(size: Int): Array<OrdenParcelable?> {
            return arrayOfNulls(size)
        }
    }

}

fun Parcel.escribirDate(date: Date?) {
    writeLong(date?.time ?: -1)
}

fun Parcel.leerDate(): Date? {
    val long = readLong()
    return if (long != -1L) Date(long) else null
}
