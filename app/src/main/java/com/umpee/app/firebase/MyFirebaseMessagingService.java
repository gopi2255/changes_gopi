package com.umpee.app.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.umpee.app.R;
import com.umpee.app.activity.MessageActivity;
import com.umpee.app.cache.Cache;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by King Jocoa on 12/17/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("MESSAGE", "onMessageReceived: ");
        // notification
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            if (TextUtils.isEmpty(title)) title = getString(R.string.app_name);

            try {
                JSONObject object = new JSONObject(body);
                int type = object.getInt("type");
                String data = object.getString("data");
                String strData = data;
                Log.i(">>>", "onMessageReceived: " + data);
                try {
                    JSONObject obj = new JSONObject(data);
                    type = obj.getInt("type");
                    strData = obj.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (type == 0 && Cache.getUser().isUmpire()) {
                    Intent intent = MessageActivity.newIntent(getApplicationContext(), strData);
                    sendNotification(intent, title, "Result is " + data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendNotification(Intent intent, String title, String message) {
        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title).setContentText(message).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}