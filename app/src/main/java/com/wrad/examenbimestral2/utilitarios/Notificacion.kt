package com.wrad.examenbimestral2.utilitarios

import android.os.AsyncTask
import kotlinx.coroutines.experimental.async
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


object Notificacion {
    val JSON = MediaType.parse("application/json; charset=utf-8")

    fun sendNotification(regToken: String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void): Void? {
                try {
                    val client = OkHttpClient()
                    val json = JSONObject()
                    val dataJson = JSONObject()
                    dataJson.put("body", "Hi this is sent from device to device")
                    dataJson.put("title", "dummy title")
                    json.put("notification", dataJson)
                    json.put("to", regToken)
                    val body = RequestBody.create(JSON, json.toString())
                    val request = Request.Builder()
                            .header("Authorization", "key=" + Constante.LEGACY_SERVER_KEY)
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build()
                    val response = client.newCall(request).execute()
                    val finalResponse = response.body().toString()
                } catch (e: Exception) {
                    //Log.d(TAG,e+"");
                }

                return null
            }
        }.execute()
    }

    fun sendNotificationAnko(regToken: String) {
        async {
            try {
                val client = OkHttpClient()
                val json = JSONObject()
                val dataJson = JSONObject()
                dataJson.put("body", "Hi this is sent from device to device")
                dataJson.put("title", "dummy title")
                json.put("notification", dataJson)
                json.put("to", regToken)
                val body = RequestBody.create(JSON, json.toString())
                val request = Request.Builder()
                        .header("Authorization", "key=" + Constante.LEGACY_SERVER_KEY)
                        .url("https://fcm.googleapis.com/fcm/send")
                        .post(body)
                        .build()
                val response = client.newCall(request).execute()
                val finalResponse = response.body().toString()
            } catch (e: Exception) {
                //Log.d(TAG,e+"");
            }
        }
    }

}