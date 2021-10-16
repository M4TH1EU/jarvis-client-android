package ch.mathieubroillet.jarvis.android;

import android.app.KeyguardManager;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ListeningActivity extends AppCompatActivity {
    private static final String audioFile = null;
    private MediaRecorder mediaRecorder;
    private RecordAudio recordTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // used for turning on the screen and show the activity
        setShowWhenLocked(true);
        setTurnScreenOn(true);

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager != null)
            keyguardManager.requestDismissKeyguard(this, null);


        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.dialog_listening);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRecording();
    }

    public void quit(View view) {
        finish();
    }

    public void recordAudio(int seconds) {
        try {
            // Byte array for audio record
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ParcelFileDescriptor[] descriptors = ParcelFileDescriptor.createPipe();
            ParcelFileDescriptor parcelRead = new ParcelFileDescriptor(descriptors[0]);
            ParcelFileDescriptor parcelWrite = new ParcelFileDescriptor(descriptors[1]);

            InputStream inputStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelRead);

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(parcelWrite.getFileDescriptor());
            mediaRecorder.setMaxDuration(seconds * 1000);
            mediaRecorder.prepare();

            mediaRecorder.start();


            int read;
            byte[] data = new byte[16384];

            while ((read = inputStream.read(data, 0, data.length)) != -1) {
                byteArrayOutputStream.write(data, 0, read);
            }

            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void startRecording() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            recordTask = new RecordAudio();
            recordTask.start();
            recordTask.execute();
        }, 250);
    }

    public void stopRecording() {
        /*
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();*/

        recordTask.stop();
        recordTask.cancel(true);

        //byteArrayOutputStream.toByteArray();
    }

    public void onClickMicrophone(View view) {
        view.setVisibility(View.INVISIBLE);

        View dots = findViewById(R.id.dots);
        dots.setVisibility(View.VISIBLE);

        //recordAudio(5);

        startRecording();


    }


    public void onClickRecordingDots(View view) {
        view.setVisibility(View.INVISIBLE);

        View dots = findViewById(R.id.microphone);
        dots.setVisibility(View.VISIBLE);

        stopRecording();
    }
}