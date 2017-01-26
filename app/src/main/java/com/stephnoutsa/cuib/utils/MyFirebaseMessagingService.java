package com.stephnoutsa.cuib.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.stephnoutsa.cuib.Messages;
import com.stephnoutsa.cuib.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by stephnoutsa on 11/23/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    NotificationCompat.Builder notification;
    SimpleDateFormat sdf;
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    String NOTIFICATION_GROUP = "Messages";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true); // Delete notification from screen after user has viewed it.

        String sender = getString(R.string.school_name);
        long lTime = remoteMessage.getSentTime();
        Date date = new Date();
        // If the remote time is correctly set, use it as sent time
        if (lTime != 0) {
            date.setTime(lTime);
        }
        String time = sdf.format(date);
        /*String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();*/
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("body");
        String ticker = trimText(message);

        // Build the notification
        notification.setSmallIcon(R.drawable.logo); // Sets icon to be displayed
        notification.setColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
        notification.setContentTitle(title);
        notification.setSound(alarmSound);
        notification.setGroup(NOTIFICATION_GROUP); // Used to group notifications
        Intent i = new Intent(this, Messages.class);

        // Preserve navigation when launching Messages activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack
        stackBuilder.addParentStack(Messages.class);

        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(i);

        // Gets a PendingIntent containing the entire back stack
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        // Issue the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Random r = new Random();

        notification.setTicker(ticker); // Sets the text displayed in app bar when notification is received
        notification.setWhen(System.currentTimeMillis());
        notification.setContentText(message);
        int rand = r.nextInt(1000);
        nm.notify(rand, notification.build());

        // Add the message to the database
        dbHandler.addMessage(sender, time, title, message);

        // Create the summary notification
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Notification summary = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.msg_summary))
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(largeIcon)
                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setStyle(new NotificationCompat.InboxStyle()
                        .setBigContentTitle(getString(R.string.msg_summary))
                        .setSummaryText(getString(R.string.app_name)))
                .setGroup(NOTIFICATION_GROUP)
                .setGroupSummary(true)
                .build();

        nm.notify(1, summary);
    }

    // Trim notification ticker text
    public static String trimText(String text) {

        String trimmed = "";
        int requiredNum = 20;
        int currentNum = text.length();

        if(requiredNum >= currentNum) {
            trimmed = text;
        }
        else {
            for(int i = 0; i < requiredNum; i++) {
                trimmed += text.charAt(i);
            }
            trimmed += "...";
        }

        return trimmed;
    }

}
