package com.example.nfnt.libraryfragment;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by NFNT on 10/14/2018.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("The token from My App", "Refreshed token: " + refreshedToken);
    }

    public void showToken()
    {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("The token from My App", "Refreshed token: " + refreshedToken);
    }
}
