package com.wrad.examenbimestral2.modelos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FotoComidaModel(
        var id: String?,
        var nombre: String?,
        var url: String?,
        var comidaId: String?
) : Parcelable {

    constructor() : this(null, null, null, null)

    constructor(
            nombre: String?,
            url: String?,
            comida: String?
    ) : this(null, nombre, url, comida)
}

