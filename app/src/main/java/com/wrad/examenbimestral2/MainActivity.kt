package com.wrad.examenbimestral2

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.wrad.examenbimestral2.actividades.comprador.CompradorActivity
import com.wrad.examenbimestral2.actividades.delivery.DeliveryActivity
import com.wrad.examenbimestral2.actividades.vendedor.VendedorActivity
import com.wrad.examenbimestral2.modelos.UsuarioModel
import com.wrad.examenbimestral2.servicios.DatabaseService
import com.wrad.examenbimestral2.utilitarios.Constante
import com.wrad.examenbimestral2.utilitarios.Mensaje
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val TAG = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        //auto logged
        val usuario = mAuth.currentUser
        if (usuario != null) {
            usuario.uid
            Log.i(TAG, usuario.uid)
            goToActivityByUserRol(usuario.uid)
        }

        //TODO solo para test, borrar
//        txt_email_login.setText("vendedor10@email.com")
//        txt_email_login.setText("comprador10@email.com")
        txt_email_login.setText("delivery10@email.com")
        txt_password_login.setText("123456")

        btn_log_in.setOnClickListener {
            autenticarUsuario(txt_email_login.text.toString(), txt_password_login.text.toString())
        }

        btn_sign_up.setOnClickListener {
            crearUsuario(txt_email_login.text.toString(), txt_password_login.text.toString())
        }
    }

    private fun goToActivityByUserRol(uid: String) {
        val databaseReferenceByModel = FirebaseDatabase.getInstance().getReference(Constante.USUARIO_FIREBASE).child(uid)
        databaseReferenceByModel.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i(TAG, databaseError.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(UsuarioModel::class.java)
                    Log.i(TAG, "Usuario logueado: " + user.toString())
                    val rol = user!!.tipo
                    when (rol) {
                        Constante.ROL_DELIVERY -> goToActivity(DeliveryActivity::class.java)
                        Constante.ROL_VENDEDOR -> goToActivity(VendedorActivity::class.java)
                        Constante.ROL_COMPRADOR -> goToActivity(CompradorActivity::class.java)
                        else -> {
                            Mensaje.emitirError(this@MainActivity, "Error al autologuear el usuario")
                            throw Exception("Error al autologuear el usuario")
                        }
                    }
                    Mensaje.emitirInformacion(this@MainActivity, "Bienvenido $rol!")
                    finish()
                }
            }
        })
    }

    private fun autenticarUsuario(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.i(TAG, "signInWithEmail:success")
                        goToActivityByUserRol(mAuth.currentUser!!.uid)
                    } else {
                        Log.i(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun crearUsuario(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val options = arrayOf(Constante.ROL_COMPRADOR, Constante.ROL_VENDEDOR, Constante.ROL_DELIVERY)
                        val builder = AlertDialog.Builder(this)
                                .setTitle("Seleccione un rol")
                                .setItems(options) { _, which ->
                                    when (which) {
                                        0 -> {
                                            insertarUsuarioFirebase(UsuarioModel(email, password, Constante.ROL_COMPRADOR))
                                            goToActivity(CompradorActivity::class.java)
                                        }
                                        1 -> {
                                            insertarUsuarioFirebase(UsuarioModel(email, password, Constante.ROL_VENDEDOR))
                                            goToActivity(VendedorActivity::class.java)
                                        }
                                        2 -> {
                                            insertarUsuarioFirebase(UsuarioModel(email, password, Constante.ROL_DELIVERY))
                                            goToActivity(DeliveryActivity::class.java)
                                        }
                                        else -> { // Note the block
                                            Mensaje.emitirError(this, "Error, comuníquese con el administrador")
                                        }
                                    }
                                }.show()
                        builder.show()
                        Mensaje.emitirInformacion(this, "Usuario creado con éxito, bienvenido.")
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Mensaje.emitirError(this, "Error al crear usuario.")
                    }
                }
    }

    private fun insertarUsuarioFirebase(nuevoUsuario: UsuarioModel) {
        DatabaseService.insertWithSpecificKey(nuevoUsuario, Constante.USUARIO_FIREBASE, mAuth.currentUser!!.uid)
    }

    private fun <T> goToActivity(genericActivityClass: Class<T>) {
        val intent = Intent(this, genericActivityClass)
        startActivity(intent)
    }

}
