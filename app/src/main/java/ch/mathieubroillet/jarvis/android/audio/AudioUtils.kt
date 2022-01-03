package ch.mathieubroillet.jarvis.android.audio

import android.os.Handler


private var recordTask: RecordAudio = RecordAudio()

fun startRecording() {
    val handler = Handler()
    handler.postDelayed({
        recordTask = RecordAudio()
        recordTask.start()
        recordTask.run()
    }, 250)
}

fun stopRecording() {
    recordTask.stop()
    recordTask.interrupt()

    //byteArrayOutputStream.toByteArray();
}
