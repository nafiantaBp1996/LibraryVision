package com.example.nfnt.libraryfragment;

/**
 * Created by NFNT on 9/17/2018.
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements GetAddress.onTaskDone, GoogleApiClient.OnConnectionFailedListener {

    //location
    private static final int REQUEST_LOCATION = 1;
    public FusedLocationProviderClient mFusedLoca;
    private GoogleApiClient googleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    TextView textViewAlamat;

    private long idImg;
    ImageView image, image2;
    private static final int SELECT_PICTURE = 1;
    public String selectedImagePath = "", url;
    Bitmap imgFileCOre, imgFileRepalcer;
    int actResult = 0;


    public DetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API).enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                .build();
        //deklarasi GoogleApiClient untuk mendapatkan API client
        //mendapatkan nama tempat dari curent place saat ini
        mFusedLoca = LocationServices.getFusedLocationProviderClient(getActivity());
        return inflater.inflate(R.layout.activity_maina, container, false);
    }

    public void setImgs(long id) {
        this.idImg = id;
    }

    public void seturl(String url) {
        this.url = url;
    }

    public Bitmap statisImg(int draw) {
        Bitmap img = BitmapFactory.decodeResource(getActivity().getResources(), draw);
        return img;
    }

    @Override
    public void onStart() {

        super.onStart();
        View view = getView();
        if (view != null) {
            image = (ImageView) view.findViewById(R.id.imageLayout);
            image2 = (ImageView) view.findViewById(R.id.imageLayout2);
            Button proses = (Button) view.findViewById(R.id.btnEmoji);
            textViewAlamat = (TextView) view.findViewById(R.id.textViewAlamat);


            ImageReplacer data = ImageReplacer.dataImage[(int) idImg];
            String gambar = "https://d3pz1jifuab5zg.cloudfront.net/2016/08/16153058/hamster-health-center-2.jpg";
            Picasso.get().load(url).into(image2);
            imgFileRepalcer = statisImg(data.getGambar_());
            image2.setImageBitmap(imgFileRepalcer);
            //wira
            imgFileRepalcer = getBitmapFromURL(url);
            image2.setImageBitmap(imgFileRepalcer);
            //syafri
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                }
            });

            proses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imgFileCOre == null) {
                        //g etadress();
                        Toast.makeText(v.getContext(), "Tidak Ada Gambar Yang Akan di Replace", Toast.LENGTH_SHORT).show();
                    } else {
                        if(actResult == 3){
                            Toast.makeText(v.getContext(), "muncul video lalala", Toast.LENGTH_SHORT).show();
                            actResult =0;
                        }else {
                        getadress();
                        Emojifier emoji = new Emojifier();
                        image.setImageBitmap(emoji.detectFaces(getContext(), imgFileCOre, imgFileRepalcer));
                        actResult ++;
                        }
                    }
                }
            });


        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                image.setVisibility(View.VISIBLE);
                image.setImageURI(selectedImageUri);
                try {
                    imgFileCOre = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //wira
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
    //syafri


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
                        PendingResult<PlaceLikelihoodBuffer> placeResult = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
                        placeResult.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                            @Override
                            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
                                for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                                    textViewAlamat.setText(placeLikelihood.getPlace().getAddress());//mengupdate text nama lokasi dengan data yang didapatkan
                                    //textViewAlamat.setText("cakper");//mengupdate text nama lokasi dengan data yang didapatkan
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
        textViewAlamat.setText("onTaskJalan");
        PendingResult<PlaceLikelihoodBuffer> placeResult = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
        placeResult.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
                for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                    textViewAlamat.setText(placeLikelihood.getPlace().getAddress());//mengupdate text nama lokasi dengan data yang didapatkan
                    //textViewAlamat.setText("cakper");//mengupdate text nama lokasi dengan data yang didapatkan
                }
                placeLikelihoods.release();
            }
        });
    }

}

