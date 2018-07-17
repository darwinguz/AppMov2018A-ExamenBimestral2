package com.wrad.examenbimestral2.utilitarios

import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wrad.examenbimestral2.R

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        const val TAG = "MyFirebaseMessaging"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        val from = remoteMessage!!.from
        Log.d(TAG, "Recibido de: $from")

        if (remoteMessage.notification != null) {
            Log.d(TAG, "Notificacion: ${remoteMessage.notification!!.body}")
            mostrarNotification(remoteMessage.notification!!.title, remoteMessage.notification!!.body)
        }

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Datos: ${remoteMessage.data}")
        }

    }

    private fun mostrarNotification(title: String?, body: String?) {
//        NotificationCompat.Builder
        val notificationBuilder = NotificationCompat
                .Builder(this)
                .setSmallIcon(R.drawable.ic_group_expand_15)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
    }
}