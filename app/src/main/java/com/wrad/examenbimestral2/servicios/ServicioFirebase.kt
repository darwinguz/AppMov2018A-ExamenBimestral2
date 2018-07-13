package com.wrad.examenbimestral2.servicios

import com.google.firebase.database.FirebaseDatabase

object ServicioFirebase {
    fun insert(any: Any, table: String) {
        val databaseTableReference = FirebaseDatabase.getInstance().getReference(table)
        val id = databaseTableReference.push().key!!
        databaseTableReference.child(id).setValue(any)
    }

}