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
                .build();

        startForeground(4002, notification);

        try {
            porcupineManager = new PorcupineManager.Builder()
                    .setKeyword(Porcupine.BuiltInKeyword.JARVIS)
                    .setSensitivity(0.7f).build(
                            getApplicationContext(),
                            (keywordIndex) -> {


                                Intent dialogIntent = new Intent(getBaseContext(), ListeningActivity.class);
                                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(dialogIntent);


                                Toast.makeText(this, "Jarvis detecté", Toast.LENGTH_SHORT).show();

                                /*Notification n = new NotificationCompat.Builder(this, CHANNEL_ID)
                                        .setContentTitle("Wake word")
                                        .setContentText("Detected")
                                        .setSmallIcon(R.drawable.ic_launcher_background)
                                        .setContentIntent(contentIntent)
                                        .build();

                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                assert notificationManager != null;
                                notificationManager.notify(1234, n);*/
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
