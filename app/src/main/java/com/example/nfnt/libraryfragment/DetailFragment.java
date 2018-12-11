package com.example.nfnt.libraryfragment;

/**
 * Created by NFNT on 9/17/2018.
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    ImageView image, image2,imgplace;
    private static final int SELECT_PICTURE = 1;
    public String url;
    Bitmap imgFileCOre, imgFileRepalcer;
    int actResult = 0;

    public static int placesWidget;

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
            imgplace=(ImageView) view.findViewById(R.id.imageViewPlace);
            Button proses = (Button) view.findViewById(R.id.btnEmoji);
            Button save = (Button) view.findViewById(R.id.btnSave);
            textViewAlamat = (TextView) view.findViewById(R.id.textViewAlamat);
            textViewAlamat.setDrawingCacheEnabled(true);


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

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imgFileCOre == null) {
                        Toast.makeText(v.getContext(), "Tidak Ada Gambar Yang Akan di Simpan", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                            image.buildDrawingCache();
                            saveImage(image.getDrawingCache());
                            Toast.makeText(v.getContext(), "Gambar Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                        }
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
                            Intent iklan = new Intent(getContext(),IklanActivity.class);
                            startActivity(iklan);
                            //Toast.makeText(v.getContext(), "muncul video lalala", Toast.LENGTH_SHORT).show();
                            actResult =0;
                        }else {
                            getadress();
                            actResult ++;
                        }
                    }
                }
            });


        }

    }

    public Bitmap drawImageAlamat(TextView text,int placess)
    {
        Emojifier emoji = new Emojifier();
        Bitmap hasil = emoji.detectFaces(getContext(), imgFileCOre, imgFileRepalcer);
        Bitmap resultBitmap = Bitmap.createBitmap(hasil.getWidth(), hasil.getHeight(), hasil.getConfig());
        Bitmap textAlamat=text.getDrawingCache();
        Bitmap place = BitmapFactory.decodeResource(getContext().getResources(),placess);
        Bitmap places = Bitmap.createScaledBitmap(place, 80, 80, false);
        int height=hasil.getHeight()-90;
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(hasil,0, 0, null);
        canvas.drawBitmap(places, 10,height, null);
        canvas.drawBitmap(textAlamat, 100,height, null);

        return resultBitmap;
    }

    public void saveImage(Bitmap bmp) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean hasPermission = (ContextCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_LOCATION);

            } else {
                Bitmap bitmap = bmp;

                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/Replacer");
                dir.mkdirs();
                String fileName = String.format(System.currentTimeMillis() + ".jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                try {
                    outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
                                    int idGambar=setTypeLocation(placeLikelihood.getPlace());
                                    image.setImageBitmap(drawImageAlamat(textViewAlamat,idGambar));
                                    WidgetIcon.placess = idGambar;
                                    WidgetIcon.adress = placeLikelihood.getPlace().getAddress().toString();
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
        return drawId; //mengembalikan nilai dari hasil seleksi kondisi untuk diakses drawabale
    }

}

