package com.example.nfnt.libraryfragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by NFNT on 11/11/2018.
 */

public class GetAddress extends AsyncTask<Location,Void,String> {

    interface  onTaskDone
    {
        void  onTaskCompleted(String result);
    }
    private Context myContext;
    private  onTaskDone myListener;
    GetAddress(Context appContext, onTaskDone listener)
    {
        myContext = appContext;
        myListener = listener;
    }
    @Override
    protected String doInBackground(Location... params) {
        Geocoder geocoder = new Geocoder(myContext, Locale.getDefault());
        Location location = params[0];
        List<Address> alamat = null;
        String resultMsg = "";



        try {
            alamat=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

        } catch (IOException e) {
            resultMsg="Service Unavailable";
            Log.e("EROR_LOCATION", resultMsg,e );
        } catch (IllegalArgumentException ilegal)
        {
            resultMsg = "Coordinat Invalid";
            Log.e("EROR_LOCATION", "location invalid ",ilegal );
        }

        if (alamat == null || alamat.size()==0)
        {
            if(resultMsg.isEmpty())
            {
                resultMsg="Can't Find the address";
                Log.e("LOCATION_EROR",resultMsg);
            }

        }
        else
        {
            Address address = alamat.get(0);
            ArrayList<String> barisAlamat = new ArrayList<>();

            for (int i = 0; i<= address.getMaxAddressLineIndex();i++)
            {
                barisAlamat.add(address.getAddressLine(i));
            }
            resultMsg = TextUtils.join("\n",barisAlamat);
        }

        return resultMsg;
    }



}
