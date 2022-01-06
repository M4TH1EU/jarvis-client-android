package ch.mathieubroillet.jarvis.android.audio

import android.content.Context
import com.github.squti.androidwaverecorder.WaveRecorder
import java.io.File


class AudioRecorder(
    private var audioTempFileOutput: String = "",
    var waveRecorder: WaveRecorder
) {


    fun startRecording() {
        waveRecorder.startRecording()
    }

    fun stopRecording() {
        waveRecorder.stopRecording()


    }

    fun getOutputFile(): File {
        return File(audioTempFileOutput)
    }
}

fun getAudioRecorder(context: Context): AudioRecorder {
    val outputFile = context.filesDir.absolutePath + "/temp_recording.wav"

    val waveRecorder: WaveRecorder = WaveRecorder(outputFile)
    waveRecorder.noiseSuppressorActive = true

    return AudioRecorder(audioTempFileOutput = outputFile, waveRecorder = waveRecorder)
}
