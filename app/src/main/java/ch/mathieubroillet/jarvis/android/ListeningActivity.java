package ch.mathieubroillet.jarvis.android;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class ListeningActivity extends AppCompatActivity {

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


        setContentView(R.layout.activity_listening);

    }

    public void quit(View view) {
        finish();
    }

    public void startRecording(View view) {
        view.setVisibility(View.INVISIBLE);

        View dots = findViewById(R.id.dots);
        dots.setVisibility(View.VISIBLE);

    }

    public void stopRecording(View view) {
        view.setVisibility(View.INVISIBLE);

        View dots = findViewById(R.id.microphone);
        dots.setVisibility(View.VISIBLE);
    }
}