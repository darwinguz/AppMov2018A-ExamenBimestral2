package com.wrad.examenbimestral2.servicios

import android.util.Log
import com.google.firebase.database.*


object DatabaseService {
    private val TAG = DatabaseService::class.java.name

    fun insertWithAutogeratedKey(any: Any, table: String) {
        val databaseTableReference = FirebaseDatabase.getInstance().getReference(table)
        val id = databaseTableReference.push().key!!
        val refChild = databaseTableReference.child(id)
        refChild.setValue(any)
        refChild.child("id").setValue(id)
    }

    fun insertWithSpecificKey(any: Any, table: String, key: String) {
        val databaseTableReference = FirebaseDatabase.getInstance().getReference(table).child(key)
        databaseTableReference.setValue(any)
        databaseTableReference.child("id").setValue(key)
    }

    fun updateAny(table: String, key: String, any: Any) {
        FirebaseDatabase.getInstance().getReference(table).child(key).setValue(any)
    }

    fun updateSpecificValue(table: String, key: String, atributteLabel: String, attributeValue: Any) {
        FirebaseDatabase.getInstance().getReference(table).child(key).child(atributteLabel).setValue(attributeValue)
    }

    fun <T> selectBy(atributteLabel: String, attributeValue: String, table: String, genericClass: Class<T>, ejecutar: (result: T) -> Unit) {
        val databaseReferenceByModel = FirebaseDatabase.getInstance().getReference(table)
        val queryRef = databaseReferenceByModel.orderByChild(atributteLabel).equalTo(attributeValue)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(TAG, databaseError.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    var genericObject: T? = null
                    for (it in dataSnapshot.children) {
                        genericObject = it.getValue(genericClass)!!
                    }
                    if (genericObject != null) {
                        ejecutar(genericObject)
                    } else {
                        Log.i(TAG, "Objeto no encontrado en la base de datos.")
                        throw Exception("Objeto no encontrado en la base de datos.")
                    }
                }
            }
        })
    }

    fun delete(atributteLabel: String, attributeValue: String, table: String) {
        val databaseReferenceByModel = FirebaseDatabase.getInstance().getReference(table)
        val queryRef = databaseReferenceByModel.orderByChild(atributteLabel).equalTo(attributeValue)
        queryRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                //TODO("not implemented")
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                //TODO("not implemented")
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                //TODO("not implemented")
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                dataSnapshot!!.ref.removeValue()
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                //TODO("not implemented")
            }

        })
    }
}