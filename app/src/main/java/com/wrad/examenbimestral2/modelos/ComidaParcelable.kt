package com.wrad.examenbimestral2.modelos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ComidaParcelable(
        var id: String?,
        var nombrePlato: String?,
        var descripcionPlato: String?,
        var nacionalidad: String?,
        var numeroPersonas: Int?,
        var picante: Boolean?,
        var ingredientes: Map<String, IngredienteParcelable>?,
        var usuario: UsuarioParcelable?
) : Parcelable {

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

    //for insert
    constructor(
            nombrePlato: String?,
            descripcionPlato: String?,
            nacionalidad: String?,
            numeroPersonas: Int?,
            picante: Boolean?,
            usuario: UsuarioParcelable?
    ) : this(null, nombrePlato, descripcionPlato, nacionalidad, numeroPersonas, picante, null, usuario)

    //for update
    constructor(
            id: String?,
            nombrePlato: String?,
            descripcionPlato: String?,
            nacionalidad: String?,
            numeroPersonas: Int?,
            picante: Boolean?,
            usuario: UsuarioParcelable?
    ) : this(id, nombrePlato, descripcionPlato, nacionalidad, numeroPersonas, picante, null, usuario)

    override fun toString(): String {
        return "id: $id, nombrePlato: $nombrePlato, descripcionPlato: $descripcionPlato, nacionalidad: $nacionalidad, numeroPersonas: $numeroPersonas, picante: $picante, ingredientes: $ingredientes, usuario: $usuario"
    }
}