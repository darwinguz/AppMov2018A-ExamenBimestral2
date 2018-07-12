package com.wrad.examenbimestral2

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.wrad.examenbimestral2.actividades.comprador.CompradorActivity
import com.wrad.examenbimestral2.actividades.delivery.DeliveryActivity
import com.wrad.examenbimestral2.actividades.vendedor.VendedorActivity
import com.wrad.examenbimestral2.utilitarios.Constantes
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val tag = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

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
                        val user = mAuth.currentUser
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
                        Toast.makeText(this, "Usuario creado con éxito, bienvenido.", Toast.LENGTH_SHORT).show()
                        val user = mAuth.currentUser
                        seleccionarRolUsuario()
                    } else {
                        Log.w(tag, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Error al crear usuario.", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun seleccionarRolUsuario() {
        val options = arrayOf(Constantes.ROL_COMPRADOR, Constantes.ROL_VENDEDOR, Constantes.ROL_DELIVERY)
        val builder = AlertDialog.Builder(this)
                .setTitle("Seleccione un rol")
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> {
                            goToActivity(CompradorActivity::class.java)
                        }
                        1 -> {
                            goToActivity(VendedorActivity::class.java)
                        }
                        2 -> {
                            goToActivity(DeliveryActivity::class.java)
                        }
                        else -> { // Note the block
                            Toast.makeText(this, "Error, comuníquese con el administrador", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        builder.show()
    }

    private fun <T> goToActivity(genericActivityClass: Class<T>) {
        val intent = Intent(this, genericActivityClass)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        //TODO go to user rol
        //updateUI(currentUser)
    }

}
