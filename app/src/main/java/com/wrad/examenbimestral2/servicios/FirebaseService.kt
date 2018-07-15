package com.wrad.examenbimestral2.servicios

import android.util.Log
import com.google.firebase.database.*


object FirebaseService {
    private val TAG = FirebaseService::class.java.name

    fun insert(any: Any, table: String) {
        val databaseTableReference = FirebaseDatabase.getInstance().getReference(table)
        val id = databaseTableReference.push().key!!
        val refChild = databaseTableReference.child(id)
        refChild.setValue(any)
        refChild.child("id").setValue(id)
    }


    fun selectBy(atributteLabel: String, attributeValue: String, table: String, ejecutar: (result: DataSnapshot) -> Unit) {
        val databaseReferenceByModel = FirebaseDatabase.getInstance().getReference(table)
        val queryRef = databaseReferenceByModel.orderByChild(atributteLabel).equalTo(attributeValue)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(TAG, databaseError.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    ejecutar(dataSnapshot)
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