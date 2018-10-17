package com.example.nfnt.libraryfragment;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by NFNT on 10/14/2018.
 */

public class MyFirebaseMessage extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        Notifier.getinstance(getApplicationContext()).displayNotif(title,body);

    }
}
