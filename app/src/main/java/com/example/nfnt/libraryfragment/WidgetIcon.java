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
    public static int placess =R.drawable.unknown;
    public static String adress ="Tidak Diketahui";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
// Construct the RemoteViews object
        SharedPreferences prefs = context.getSharedPreferences(SHARE_PREF_FILE,0);
        int count = prefs.getInt(COUNT_KEY + appWidgetId,0);
        //count ++;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_icon);
        WidgetIcon lala = new WidgetIcon();
        //lala.setTipeLokasi(DetailFragment.placesWidget);

        views.setImageViewResource(R.id.icon,placess);
        views.setTextViewText(R.id.textAlamat,adress);

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
}

