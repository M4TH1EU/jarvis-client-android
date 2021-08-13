package ch.mathieubroillet.jarvis.android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notifications {

    public static final String CHANNEL_ID = "JarvisChannel";


    public static void createNotificationChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Jarvis",
                    NotificationManager.IMPORTANCE_HIGH);

            manager.createNotificationChannel(notificationChannel);
        }
    }
}
