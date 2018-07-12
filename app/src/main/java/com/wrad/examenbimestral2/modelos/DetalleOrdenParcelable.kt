package com.wrad.examenbimestral2.modelos

import android.os.Parcel
import android.os.Parcelable

class DetalleOrdenParcelable(
        var id: String,
        var comida: ComidaParcelable?,
        var precio: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(ComidaParcelable::class.java.classLoader),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(comida, flags)
        parcel.writeDouble(precio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetalleOrdenParcelable> {
        override fun createFromParcel(parcel: Parcel): DetalleOrdenParcelable {
            return DetalleOrdenParcelable(parcel)
        }

        override fun newArray(size: Int): Array<DetalleOrdenParcelable?> {
            return arrayOfNulls(size)
        }
    }

}