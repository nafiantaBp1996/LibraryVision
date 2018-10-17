package com.example.nfnt.libraryfragment;

import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

/**
 * Created by NFNT on 10/14/2018.
 */

public class Notifier {

    private Context mCtx ;
    private static Notifier notifiers;

    private Notifier (Context context)
    {
        mCtx = context;
    }

    public static synchronized Notifier getinstance (Context context)
    {
        if(notifiers==null)
        {
            notifiers = new Notifier(context);
        }
        return notifiers;
    }

    public void displayNotif(String title,String body)
    {
        NotificationCompat.Builder notifBuild = (NotificationCompat.Builder) new NotificationCompat.Builder(mCtx).setSmallIcon(R.drawable.frown).setContentTitle(title).setContentText(body).setPriority(NotificationCompat.PRIORITY_DEFAULT);;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mCtx);
        notificationManager.notify(1, notifBuild.build());

    }
}
