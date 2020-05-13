package com.example.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.Random;

public class Notificaciones {

    private String ID_CHANEL;
    private String CHANEL_NAME;
    private String CHANEL_DESCRIPTION;
    NotificationManager notificationManager;

    public Notificaciones(String ID_CHANEL, String CHANEL_NAME, String CHANEL_DESCRIPTION) {
        this.ID_CHANEL = ID_CHANEL;
        this.CHANEL_DESCRIPTION = CHANEL_DESCRIPTION;
        this.CHANEL_NAME = CHANEL_NAME;
    }


    public void createNotificationChannel(MainActivity activity) {
        notificationManager = activity.pilla(notificationManager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ID_CHANEL, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANEL_DESCRIPTION);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            channel.enableVibration(true);
            channel.setShowBadge(true);
            

            notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        String titulo = "Titulo de la notificacion de REME";
        String descripcion = "Esta es una descripción de la notificacion de REME";

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(activity, "ID_REME_CHANNEL")
                .setSmallIcon(R.drawable.lobo)
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(activity.onClick());


        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;

        Notification