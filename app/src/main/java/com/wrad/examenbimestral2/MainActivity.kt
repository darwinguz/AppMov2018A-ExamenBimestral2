package com.wrad.examenbimestral2

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.wrad.examenbimestral2.actividades.comprador.CompradorActivity
import com.wrad.examenbimestral2.actividades.delivery.DeliveryActivity
import com.wrad.examenbimestral2.actividades.vendedor.VendedorActivity
import com.wrad.examenbimestral2.modelos.DetalleOrdenParcelable
import com.wrad.examenbimestral2.modelos.UsuarioParcelable
import com.wrad.examenbimestral2.utilitarios.Constante
import com.wrad.examenbimestral2.utilitarios.Mensaje
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val tag = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        //TODO solo para test, borrar
        txt_email_login.setText("vendedor10@email.com")
        txt_password_login.setText("123456")

        btn_log_in.setOnClickListener {
            autenticarUsuario(txt_email_login.text.toString(), txt_password_login.text.toString())
        }

        btn_sign_up.setOnClickListener {
            crearUsuario(txt_email_login.text.toString(), txt_password_login.text.toString())
        }
    }

    private fun autenticarUsuario(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(tag, "signInWithEmail:success")
//                        val user = mAuth.currentUser
                    } else {
                        Log.w(tag, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun crearUsuario(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(tag, "createUserWithEmail:success")
                        val options = arrayOf(Constante.ROL_COMPRADOR, Constante.ROL_VENDEDOR, Constante.ROL_DELIVERY)
                        val builder = AlertDialog.Builder(this)
                                .setTitle("Seleccione un rol")
                                .setItems(options) { _, which ->
                                    when (which) {
                                        0 -> {
                                            insertarUsuarioFirebase(UsuarioParcelable(email, password, Constante.ROL_COMPRADOR))
                                            goToActivity(CompradorActivity::class.java)
                                        }
                                        1 -> {
                                            insertarUsuarioFirebase(UsuarioParcelable(email, password, Constante.ROL_VENDEDOR))
                                            goToActivity(VendedorActivity::class.java)
                                        }
                                        2 -> {
                                            insertarUsuarioFirebase(UsuarioParcelable(email, password, Constante.ROL_DELIVERY))
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
                        Log.w(tag, "createUserWithEmail:failure", task.exception)
                        Mensaje.emitirError(this, "Error al crear usuario.")
                    }
                }
    }

    private fun insertarUsuarioFirebase(nuevoUsuario: UsuarioParcelable) {
        val userId = mAuth.currentUser!!.uid
        Log.i(tag, userId)
        Log.i(tag, nuevoUsuario.toString())
        FirebaseDatabase
                .getInstance()
                .reference.child(Constante.USUARIO_FIREBASE)
                .child(userId).setValue(nuevoUsuario)
    }

    private fun <T> goToActivity(genericActivityClass: Class<T>) {
        val intent = Intent(this, genericActivityClass)
        startActivity(intent)
    }

//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth.currentUser
//        //TODO go to user rol
//        //updateUI(currentUser)
//        Log.i(tag, "OnStart...............................")
//    }

}
