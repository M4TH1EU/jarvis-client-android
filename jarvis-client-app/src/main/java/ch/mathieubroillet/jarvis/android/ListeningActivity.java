package ch.mathieubroillet.jarvis.android;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import ai.picovoice.android.R;

public class ListeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // used for turning on the screen and show the activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager != null)
                keyguardManager.requestDismissKeyguard(this, null);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }

        setContentView(R.layout.activity_listening);

    }
}