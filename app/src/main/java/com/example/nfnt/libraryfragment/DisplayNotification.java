package com.example.nfnt.libraryfragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by NFNT on 10/16/2018.
 */

public class DisplayNotification {

    private Context myCntx ;
    private static DisplayNotification mInsNotif;

    private DisplayNotification (Context context)
    {
        myCntx = context;
    }

    public static synchronized DisplayNotification getMyInstance (Context context)
    {
        if (mInsNotif == null)
        {
            mInsNotif = new DisplayNotification(context);
        }

        return mInsNotif;
    }

//    public void displayNotif(String title, String body)
//    {
//        String CHANNEL_ID = "my_channel";
//
//        Intent theAactivity = new Intent(myCntx,MainActivity.class);
//
//        PendingIntent pdngIntent = PendingIntent.getActivity(myCntx,0,theAactivity,PendingIntent.FLAG_ONE_SHOT);
//        //MainActivity.cont = body;
//        //MainActivity.titles = title;
//
//
//        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder myBuildNotif= (NotificationCompat.Builder) new NotificationCompat.Builder(myCntx)
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setAutoCancel(true)
//                .setSound(defaultSound)
//                .setContentIntent(pdngIntent);
//
//        NotificationManager myNotifMgr = (NotificationManager) myCntx.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (myNotifMgr != null)
//        {
//            myNotifMgr.notify(1,myBuildNotif.build());
//        }
//    }

    //wira
    public void displayNotif(String tittle,String body,String gambar,String nama)
    {
        String CHANNEL_ID = "my_channel";

        Intent theAactivity = new Intent(myCntx,MainActivity.class);
        //untuk memparsing nilai dari data json yang baru dikirim maka diperlukan function putextra ketika berpindah activity

        theAactivity.putExtra("judul",tittle);
        theAactivity.putExtra("isi",body);
        theAactivity.putExtra("gambar",gambar);
        theAactivity.putExtra("nama",nama);


        PendingIntent pdngIntent = PendingIntent.getActivity(myCntx,0,theAactivity,PendingIntent.FLAG_ONE_SHOT);
        //MainActivity.cont = body;
        //MainActivity.titles = title;


        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder myBuildNotif= (NotificationCompat.Builder) new NotificationCompat.Builder(myCntx)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(tittle)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pdngIntent);

        NotificationManager myNotifMgr = (NotificationManager) myCntx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (myNotifMgr != null)
        {
            myNotifMgr.notify(1,myBuildNotif.build());
        }
    }
    //syafri


}
