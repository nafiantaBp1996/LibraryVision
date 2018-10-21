package com.example.nfnt.libraryfragment;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by NFNT on 10/14/2018.
 */

public class MyFirebaseMessage extends FirebaseMessagingService{
    private static final String TAG = "FaceReplacer";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG,"Pengirim :" + remoteMessage.getFrom());

        if ( remoteMessage . getData (). size () > 0 ) {
            Log . d ( TAG , "Message: " + remoteMessage . getData (). get ( "body" ));
        }

        DisplayNotification.getMyInstance(this).displayNotif(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
    }
}
