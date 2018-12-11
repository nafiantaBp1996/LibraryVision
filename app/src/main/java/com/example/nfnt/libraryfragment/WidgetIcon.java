package com.example.nfnt.libraryfragment;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetIcon extends AppWidgetProvider implements GetAddress.onTaskDone, GoogleApiClient.OnConnectionFailedListener{
    private static final String SHARE_PREF_FILE = "appwidgetku";
    private static final String COUNT_KEY = "count";
    private static final int lala =0;
    public static int drawable = R.drawable.airport;
    //naf
    //location
    private static final int REQUEST_LOCATION = 1;
    public FusedLocationProviderClient mFusedLoca;
    private GoogleApiClient googleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    TextView textViewAlamat;

    private long idImg;
    ImageView image, image2,imgplace;
    private static final int SELECT_PICTURE = 1;
    public String url;
    Bitmap imgFileCOre, imgFileRepalcer;
    int actResult = 0;

    public static int placesWidget;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
// Construct the RemoteViews object
        SharedPreferences prefs = context.getSharedPreferences(SHARE_PREF_FILE,0);
        int count = prefs.getInt(COUNT_KEY + appWidgetId,0);
        //count ++;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_icon);
        WidgetIcon lala = new WidgetIcon();
        lala.setTipeLokasi(DetailFragment.placesWidget);
        lala.getadress();

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

    //nafi
    //location
    private void getadress() {

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Log.d("GET_PERMISSION", "getlocation: permission are granted");
            mFusedLoca.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        new GetAddress(getActivity(), DetailFragment.this).execute(location);
                        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        com.google.android.gms.common.api.PendingResult<PlaceLikelihoodBuffer> placeResult = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
                        placeResult.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                            @Override
                            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
                                for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                                    textViewAlamat.setText(placeLikelihood.getPlace().getAddress());//mengupdate text nama lokasi dengan data yang didapatkan
                                    //textViewAlamat.setText("cakper");//mengupdate text nama lokasi dengan data yang didapatkan
                                    int idGambar=setTypeLocation(placeLikelihood.getPlace());
                                    placesWidget = idGambar;
                                    break;
                                }
                                placeLikelihoods.release();
                            }
                        });
                        Log.d("GET_PERMISSION","founded");
                    }
                    else
                    {
                        textViewAlamat.setText("Your Location Unavailable");
                    }
                }
            });
        }
        //textLocation.setText("Find Your Address");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { //overide methode dari GoogleAPI client untuk memeriksa koneksi dari API yang dugunakan
        Log.e("CON_API", "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(getActivity(), "Google Places API connection failed with error code:" + connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onTaskCompleted(String result) throws SecurityException {

        com.google.android.gms.common.api.PendingResult<PlaceLikelihoodBuffer> placeResult = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
        placeResult.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
                for (PlaceLikelihood placeLikelihood : placeLikelihoods) {

                    //textViewAlamat.setText("cakper");//mengupdate text nama lokasi dengan data yang didapatkan
                }
                placeLikelihoods.release();
            }
        });
    }

    private int setTypeLocation(Place currentPlace)//methode yang digunakan untuk mendapatkan gambar sesuai dengan tempat yang dipilih
    {
        int drawId = -1;// set aawal dari variabel
        for (Integer placeType : currentPlace.getPlaceTypes())//melakukan perulangan untuk mendapatkan jenis tempat yang sesuai dari current place
        {
            switch (placeType)
            {
                case  Place.TYPE_UNIVERSITY:
                    drawId= R.drawable.school;
                    break;
                case  Place.TYPE_CAFE:
                    drawId= R.drawable.coffeeshop;
                    break;
                case  Place.TYPE_SHOPPING_MALL:
                    drawId= R.drawable.mall;
                    break;
                case  Place.TYPE_MOVIE_THEATER:
                    drawId= R.drawable.cinema;
                    break;

                case  Place.TYPE_CEMETERY:
                    drawId= R.drawable.tombstone;
                    break;
                case  Place.TYPE_NIGHT_CLUB:
                    drawId= R.drawable.haram;
                    break;
                case  Place.TYPE_MOSQUE:
                    drawId= R.drawable.mosque;
                    break;
            }
            if(drawId<0)
            {
                drawId=R.drawable.notfound; //jika tidak ditemukan maka variabel akan diganti dengan gambar not found
            }
        }
        drawable=drawId;
        return drawId; //mengembalikan nilai dari hasil seleksi kondisi untuk diakses drawabale
    }

}

