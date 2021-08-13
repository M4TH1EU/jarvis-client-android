package ch.mathieubroillet.jarvis.android;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Main extends AppCompatActivity {
    private boolean hasPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.SYSTEM_ALERT_WINDOW}, 0);
    }

    private void startService() {
        Intent serviceIntent = new Intent(this, PorcupineService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void stopService() {
        Intent serviceIntent = new Intent(this, PorcupineService.class);
        stopService(serviceIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Notifications.createNotificationChannel(getSystemService(NotificationManager.class));

        setContentView(R.layout.activity_main);

        if (!hasPermissions()) {
            Toast.makeText(this, "No pemerssions, accept all permissions and restart app.", Toast.LENGTH_SHORT).show();

            requestPermissions();
        } else {
            startService();
        }
    }
}
