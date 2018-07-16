package com.wrad.examenbimestral2.modelos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ComidaModel(
        var id: String?,
        var nombrePlato: String?,
        var descripcionPlato: String?,
        var nacionalidad: String?,
        var numeroPersonas: Int?,
        var picante: Boolean?,
        var ingredientes: Map<String, IngredienteModel>?,
        var usuario: UsuarioModel?,
        var fotos: Map<String, FotoComidaModel>?
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
            null
    )

    constructor(id: String?) : this(
            id,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    )

    //for insert
    constructor(
            nombrePlato: String?,
            descripcionPlato: String?,
            nacionalidad: String?,
            numeroPersonas: Int?,
            picante: Boolean?,
            usuario: UsuarioModel?
    ) : this(null, nombrePlato, descripcionPlato, nacionalidad, numeroPersonas, picante, null, usuario, null)

    override fun toString(): String {
        return "id: $id, nombrePlato: $nombrePlato, descripcionPlato: $descripcionPlato, nacionalidad: $nacionalidad, numeroPersonas: $numeroPersonas, picante: $picante, ingredientes: $ingredientes, usuario: $usuario"
    }
}