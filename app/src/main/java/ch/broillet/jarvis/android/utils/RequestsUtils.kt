package ch.broillet.jarvis.android.utils

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException

fun contactServerWithFileAudioRecording(file: File): String {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://jarvis-server.broillet.ch/process_voice")
        .post(file.asRequestBody("audio/x-wav; charset=utf-8".toMediaType()))
        .build()


    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        return response.body!!.string()
    }

    /* # SEND STT REQUEST DIRECTLY TO WHISPER ASR WITHOUT JARVIS SERVER API
    val okHttpClient = OkHttpClient()
        .newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    val fileBody: RequestBody = RequestBody.create("audio/x-wav".toMediaTypeOrNull(), file.readBytes())

    val multipartBody: MultipartBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM) // Header to show we are sending a Multipart Form Data
        .addFormDataPart("audio_file", file.name, fileBody) // file param
        .build()

    val request = Request.Builder()
        .addHeader("Accept", "application/json")
        .addHeader("Content-Type", "multipart/form-data")
        .url("http://192.168.1.208:9000/asr?task=transcribe&language=fr&output=json")
        .post(multipartBody)
        .build()

    okHttpClient.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        return response.body!!.string()
    }*/
}