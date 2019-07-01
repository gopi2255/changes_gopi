package com.umpee.app.firebase;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by King Jocoa on 7/15/17.
 */

public class FirebaseUtils {
    public static void registerTopic(String topic){
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
    }
}
