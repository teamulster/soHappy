package de.hsaugsburg.teamulster.sohappy.sync

import com.google.gson.Gson
import de.hsaugsburg.teamulster.sohappy.viewmodel.MeasurementViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.time.Instant
import java.util.*

/**
 * This class represents a sample implementation for a remote site.
 * */
class RemoteSite : RemoteSync {
    private val server: String = "https://lively.craftam.app/"
    private val client = OkHttpClient()
    private val gson = Gson()

    override fun synchronise(measurements: List<MeasurementViewModel>) {
        for (measurement in measurements) {
            val postJsonObject = gson.toJson(measurement)
            val json: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = postJsonObject.toRequestBody(json)
            val request = Request.Builder()
                .url("${server}insert")
                .post(requestBody)
                .build()
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            response.close()
        }
    }

    override fun getLatestSyncTimeStamp(id: String): Date {
        val request = Request.Builder()
            .url("${server}latest?userId=$id")
            .get()
            .build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }
        return Date.from(Instant.parse(gson.fromJson(response.body?.string(), String::class.java)))
    }
}
