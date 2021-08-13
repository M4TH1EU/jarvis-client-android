package ch.mathieubroillet.jarvis.android;


import android.app.NotificationChannel;
import android.app.NotificationManager;

public class Notifications {

    public static final String CHANNEL_ID = "JarvisChannel";

    public static void createNotificationChannel(NotificationManager manager) {
        NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID,
                "Jarvis",
                NotificationManager.IMPORTANCE_HIGH);

        manager.createNotificationChannel(notificationChannel);
    }
}
