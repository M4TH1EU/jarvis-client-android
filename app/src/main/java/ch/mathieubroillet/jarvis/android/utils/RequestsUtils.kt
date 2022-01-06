package ch.mathieubroillet.jarvis.android.utils

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

fun contactServerWithFileAudioRecording(file: File): String {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("http://192.168.1.130:5000/process_audio_request_file")
        .post(file.asRequestBody("audio/mpeg; charset=utf-8".toMediaType()))
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        return response.body!!.string()
    }
}