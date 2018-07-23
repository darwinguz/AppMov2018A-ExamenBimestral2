package com.wrad.examenbimestral2.utilitarios

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.experimental.async
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


object Notificacion {
    const val TAG = "Notificacion"

    fun sendNotification(tokenDeviceToSend: String, title: String, mensaje: String) {
        val JSON = MediaType.parse("application/json; charset=utf-8")
        async {
            try {
                val client = OkHttpClient()
                val json = JSONObject()
                val dataJson = JSONObject()
                dataJson.put("body", mensaje)
                dataJson.put("title", title)
                json.put("notification", dataJson)
                json.put("to", tokenDeviceToSend)
                val body = RequestBody.create(JSON, json.toString())
                val request = Request.Builder()
                        .header("Authorization", "key=" + Constante.LEGACY_SERVER_KEY)
                        .url("https://fcm.googleapis.com/fcm/send")
                        .post(body)
                        .build()
                val response = client.newCall(request).execute()
                val finalResponse = response.body()
                Log.d(TAG, finalResponse.toString())
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    fun getTokenDevice(): String? {
        return FirebaseInstanceId.getInstance().token
    }
}