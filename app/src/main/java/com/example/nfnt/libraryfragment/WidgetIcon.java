package com.example.nfnt.libraryfragment;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetIcon extends AppWidgetProvider {
    private static final String SHARE_PREF_FILE = "appwidgetku";
    private static final String COUNT_KEY = "count";
    private static final int lala =0;
    public static int drawable = R.drawable.airport;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
// Construct the RemoteViews object
        SharedPreferences prefs = context.getSharedPreferences(SHARE_PREF_FILE,0);
        int count = prefs.getInt(COUNT_KEY + appWidgetId,0);
        count ++;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_icon);
        WidgetIcon lala = new WidgetIcon();
        lala.setTipeLokasi(count);

        views.setImageViewResource(R.id.icon,drawable);
        SharedPreferences.Editor predEditor = prefs.edit();
        predEditor.putInt(COUNT_KEY + appWidgetId,count);
        predEditor.apply();
        // Instruct the widget manager to update the widget

        //setup button update
        Intent intentUpdate = new Intent(context,WidgetIcon.class);
        //action intent harus sebagai app widget update
        intentUpdate.setAction(appWidgetManager.ACTION_APPWIDGET_UPDATE);

        //masukkan id dari widget yang akan diupdate
        int[] idArray =new int[]{appWidgetId};
        intentUpdate.putExtra(appWidgetManager.EXTRA_APPWIDGET_IDS,idArray);

        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context,appWidgetId,intentUpdate,PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.icon,pendingUpdate);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    private void  setTipeLokasi(int currentPlace){
        int drawableId= -1;

        switch (currentPlace){
            case 1:
                drawableId = R.drawable.kampus;
                break;
            case 2:
                drawableId = R.drawable.warkop;
                break;
            case 3:
                drawableId = R.drawable.toko;
                break;
            case 4:
                drawableId = R.drawable.bioskop;
                break;
            //code tambahan untuk jenis tempat
            case 5:
                drawableId = R.drawable.airport;
                break;
            case 6:
                drawableId = R.drawable.atm;
                break;
            case 7:
                drawableId = R.drawable.mosque;
                break;

        }

        if(drawableId < 0){
            drawableId = R.drawable.unknown;
        }
        drawable=drawableId;
//        mAndroidImageView.setImageResource(drawableId);
    }
}

