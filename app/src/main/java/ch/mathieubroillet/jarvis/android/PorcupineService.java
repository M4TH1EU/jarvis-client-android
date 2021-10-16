package ch.mathieubroillet.jarvis.android;


import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import ai.picovoice.porcupine.Porcupine;
import ai.picovoice.porcupine.PorcupineException;
import ai.picovoice.porcupine.PorcupineManager;

public class PorcupineService extends Service {

    private PorcupineManager porcupineManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = new NotificationCompat.Builder(this, Notifications.CHANNEL_ID)
                .setContentTitle("Jarvis")
                .setContentText("Say 'Jarvis' and ask me anything.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        startForeground(4002, notification);

        try {
            porcupineManager = new PorcupineManager.Builder()
                    .setKeyword(Porcupine.BuiltInKeyword.JARVIS)
                    .setSensitivity(0.7f).build(
                            getApplicationContext(),
                            (keywordIndex) -> {

                                Intent dialogIntent = new Intent(getApplicationContext(), ListeningActivity.class);
                                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(dialogIntent);

                                Toast.makeText(this, "Wake-word detected!", Toast.LENGTH_SHORT).show();
                            });
            porcupineManager.start();
        } catch (PorcupineException e) {
            Log.e("JARVIS", e.toString());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        try {
            porcupineManager.stop();
            porcupineManager.delete();
        } catch (PorcupineException e) {
            Log.e("JARVIS", e.toString());
        }

        super.onDestroy();
    }
}
