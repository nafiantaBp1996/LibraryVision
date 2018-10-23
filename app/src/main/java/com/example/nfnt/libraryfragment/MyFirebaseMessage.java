package com.example.nfnt.libraryfragment;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by NFNT on 10/14/2018.
 */

public class MyFirebaseMessage extends FirebaseMessagingService{
    private static final String TAG = "FaceReplacer";
    String judul,isi,gambar,nama;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG,"Pengirim :" + remoteMessage.getFrom());

        if ( remoteMessage . getData (). size () > 0 ) {
            Log . d ( TAG , "Message: " + remoteMessage . getData (). get ( "body" ));
            //wira
            // code dibawah ini berfungsi untuk mengambil nilai data dari json untuk selanjutnya dimasukkan kedalam parameter di syntax terbawah
            judul =remoteMessage.getData().get("title");
            isi = remoteMessage.getData().get("body");
            gambar = remoteMessage.getData().get("gambar");
            nama =remoteMessage.getData().get("nama");
            //syafri
        }

//        DisplayNotification.getMyInstance(this).displayNotif(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));

        //wira
        DisplayNotification.getMyInstance(this).displayNotif(judul,isi,gambar,nama);
    }
}
