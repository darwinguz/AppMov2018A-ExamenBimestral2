package com.wrad.examenbimestral2.actividades.vendedor

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.util.Log
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.wrad.examenbimestral2.R
import com.wrad.examenbimestral2.adapters.FotosComidaAdapter
import com.wrad.examenbimestral2.modelos.ComidaModel
import com.wrad.examenbimestral2.modelos.FotoComidaModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante
import com.wrad.examenbimestral2.utilitarios.Mensaje
import kotlinx.android.synthetic.main.activity_fotos_comida.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FotosComidaActivity : AppCompatActivity() {
    private lateinit var storageRef: StorageReference
    private lateinit var comida: ComidaModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var fotos: ArrayList<FotoComidaModel>


    companion object {
        private const val TAG = "FotosComidaActivity"
        private const val TOMAR_FOTO_REQUEST = 1
        private const val RESULTADO_PERMISO_CAMARA = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos_comida)

        //permisos
        val permisosDeCamara = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val noTienePermisosDeCamara = permisosDeCamara != PackageManager.PERMISSION_GRANTED

        if (noTienePermisosDeCamara) {
            Log.i(TAG, "Entrando a pedir permiso de CAMARA")
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA,
                            Manifest.permission.SEND_SMS),
                    RESULTADO_PERMISO_CAMARA
            )
        } else {
            Log.i(TAG, "Ya tiene este permiso de CAMARA")
        }


        storageRef = FirebaseStorage.getInstance().reference

        comida = intent.getParcelableExtra("comida-intent")

        btn_crear_fotos_comida.setOnClickListener {
            tomarFoto()
        }

        if (comida.fotos != null) {
            fotos = ArrayList(comida.fotos!!.values)
        } else {
            fotos = ArrayList()
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = FotosComidaAdapter(fotos)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view_fotos_comida).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

    }

    var directorioActualImagen = ""
    private fun tomarFoto() {
        val archivoImagen = crearArchivo("JPEG_", Environment.DIRECTORY_PICTURES, ".jpg")
        directorioActualImagen = archivoImagen.absolutePath

        enviarIntentFoto(archivoImagen)
    }

    private fun enviarIntentFoto(archivo: File) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.wrad.examenbimestral2.fileprovider",
                archivo)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, TOMAR_FOTO_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            TOMAR_FOTO_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val fotoActualBitmap = BitmapFactory
                            .decodeFile(directorioActualImagen)
                    subirFoto()
                    //TODO imagen preview
//                    image_view_camera2.setImageBitmap(fotoActualBitmap)
                }
            }
        }
    }

    private fun subirFoto() {
        val file = Uri.fromFile(File(directorioActualImagen))

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT //or InputType.TYPE_TEXT_VARIATION_PASSWORD

        val builder = AlertDialog
                .Builder(this)
                .setTitle("Ingrese el nombre de la foto:")
                .setView(input)
                .setPositiveButton("OK") { dialog, which ->
                    val textInput = input.text.toString()
                    val fileName = "$textInput.jpg"
                    val photosRef = storageRef.child("${FirebaseAuth.getInstance().currentUser!!.uid}/${comida.id}/$fileName")
                    photosRef.putFile(file)
                            .addOnSuccessListener { taskSnapshot ->
                                // Get a URL to the uploaded content
                                val downloadUrl = taskSnapshot.downloadUrl
                                Log.i(TAG, downloadUrl.toString())
                                Mensaje.emitirInformacion(this, "Imagen guardada con exito.")
                                val fotoToInsert = FotoComidaModel(fileName, downloadUrl.toString(), comida.id!!)
                                DatabaseService.insertWithAutogeratedKey(fotoToInsert, Constante.getReferenceFotosComida(comida.id!!)) {
                                    //TODO implementar refresh
                                    fotoToInsert.id = it
                                    fotos.add(fotoToInsert)
                                    viewAdapter.notifyItemInserted(fotos.size)
                                    viewAdapter.notifyDataSetChanged()
                                    Mensaje.emitirInformacion(this, "Imagen actualizada y subida a la RealTimeDatabase con exito.")
                                }
                            }
                            .addOnFailureListener {
                                // Handle unsuccessful uploads
                                Log.i(TAG, "Error al subir foto.")
                            }
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
        builder.show()
    }

    private fun crearArchivo(prefijo: String, directorio: String, extension: String): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = prefijo + timeStamp + "_"
        val storageDir = getExternalFilesDir(directorio)
        //si no tiene sdcard
        // val storageDir = getFilesDir()

        return File.createTempFile(
                imageFileName, /* prefix */
                extension, /* suffix */
                storageDir      /* directory */
        )
    }

    private fun loadImageFromStorage() {
//        try {
//            val path = pathArray.get(array_position)
//            val f = File(path, "")
//            val b = BitmapFactory.decodeStream(FileInputStream(f))
//            image.setImageBitmap(b)
//        } catch (e: FileNotFoundException) {
//            Log.e(FragmentActivity.TAG, "loadImageFromStorage: FileNotFoundException: " + e.getMessage())
//        }

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray) {
        when (requestCode) {
            RESULTADO_PERMISO_CAMARA -> {
                Log.i("etiqueta", "Recibimos resultado")
            }
        }
    }
}


class GenericFileProvider : FileProvider() {

}