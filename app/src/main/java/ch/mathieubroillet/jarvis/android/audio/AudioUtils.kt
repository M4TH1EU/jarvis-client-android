package ch.mathieubroillet.jarvis.android.audio

import android.content.Context
import ch.mathieubroillet.jarvis.android.utils.contactServerWithFileAudioRecording
import com.github.squti.androidwaverecorder.RecorderState
import com.github.squti.androidwaverecorder.WaveRecorder
import java.io.File
import kotlin.concurrent.thread

private var audioTempFileOutput: String = ""
private var waveRecorder: WaveRecorder? = null
private var isRecording: Boolean = false

fun startRecording() {
    if (waveRecorder != null) {
        waveRecorder!!.startRecording()
    }
}


fun stopRecording() {
    if (waveRecorder != null) {
        waveRecorder!!.stopRecording()
    }

    thread {
        contactServerWithFileAudioRecording(getOutputFile())
        getOutputFile().delete()
    }
}

fun getOutputFile(): File {
    return File(audioTempFileOutput)
}

fun isRecording(): Boolean {
    return isRecording
}

fun registerRecorder(context: Context) {
    if (waveRecorder == null) {
        audioTempFileOutput = context.filesDir.absolutePath + "/temp_recording.wav"
        waveRecorder = WaveRecorder(audioTempFileOutput)
        waveRecorder!!.noiseSuppressorActive = true


        waveRecorder!!.onStateChangeListener = {
            when (it) {
                RecorderState.RECORDING -> {
                    isRecording = true
                }
                RecorderState.STOP -> {
                    isRecording = false
                }
                else -> {}
            }
        }
    }
}