package com.wrad.examenbimestral2.servicios

import android.util.Log
import com.google.firebase.database.*


object DatabaseService {
    private val TAG = DatabaseService::class.java.name

    fun insertWithAutogeratedKey(any: Any, referece: String) {
        val databaseTableReference = FirebaseDatabase.getInstance().getReference(referece)
        val id = databaseTableReference.push().key!!
        val refChild = databaseTableReference.child(id)
        refChild.setValue(any)
        refChild.child("id").setValue(id)
    }

    fun insertWithSpecificKey(any: Any, reference: String, key: String) {
        val databaseTableReference = FirebaseDatabase.getInstance().getReference(reference).child(key)
        databaseTableReference.setValue(any)
        databaseTableReference.child("id").setValue(key)
    }

    fun <T> selectAll(reference: String, genericClass: Class<T>, ejecutar: (results: List<T>) -> Unit) {
        val databaseReferenceByModel = FirebaseDatabase.getInstance().getReference(reference)
        databaseReferenceByModel.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(TAG, databaseError.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    var genericObject: T
                    val objects = ArrayList<T>()
                    for (it in dataSnapshot.children) {
                        genericObject = it.getValue(genericClass)!!
                        objects.add(genericObject)
                    }
                    ejecutar(objects)
                }
            }
        })
    }


    fun <T> selectSingleWhere(atributteLabel: String, attributeValue: String, reference: String, genericClass: Class<T>, ejecutar: (result: T) -> Unit) {
        val databaseReferenceByModel = FirebaseDatabase.getInstance().getReference(reference)
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
                    //TODO validar single result
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

    fun <T> selectSingleByKey(key: String, reference: String, genericClass: Class<T>, ejecutar: (result: T) -> Unit) {
        val databaseReferenceByModel = FirebaseDatabase.getInstance().getReference(reference)
        val queryRef = databaseReferenceByModel.orderByKey().equalTo(key)
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
                    //TODO validar single result
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

    fun <T> selectAllFilterByAttribute(atributteLabel: String, attributeValue: String, reference: String, genericClass: Class<T>, ejecutar: (result: List<T>) -> Unit) {
        val databaseReferenceByModel = FirebaseDatabase.getInstance().getReference(reference)
        val queryRef = databaseReferenceByModel.orderByChild(atributteLabel).equalTo(attributeValue)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(TAG, databaseError.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    var genericObject: T
                    val objects = ArrayList<T>()
                    for (it in dataSnapshot.children) {
                        genericObject = it.getValue(genericClass)!!
                        objects.add(genericObject)
                    }
                    ejecutar(objects)
                }
            }
        })
    }

    fun updateAny(reference: String, key: String, any: Any) {
        FirebaseDatabase.getInstance().getReference(reference).child(key).setValue(any)
    }

    fun updateSpecificValue(referece: String, key: String, atributteLabel: String, attributeValue: Any) {
        FirebaseDatabase.getInstance().getReference(referece).child(key).child(atributteLabel).setValue(attributeValue)
    }

    fun deleteByKey(key: String, reference: String) {
        FirebaseDatabase.getInstance().getReference(reference).child(key).removeValue()
    }

    fun deleteByAttribute(atributteLabel: String, attributeValue: String, reference: String) {
        val databaseReferenceByModel = FirebaseDatabase.getInstance().getReference(reference)
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