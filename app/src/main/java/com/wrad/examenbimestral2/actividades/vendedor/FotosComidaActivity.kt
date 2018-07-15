package com.wrad.examenbimestral2.actividades.vendedor

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.modelos.ComidaModel


class FotosComidaActivity : AppCompatActivity() {
    private lateinit var mStorageRef: StorageReference
    private lateinit var comida: ComidaModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos_comida)

        mStorageRef = FirebaseStorage.getInstance().reference

        comida = intent.getParcelableExtra("comida-intent")


    }
}
