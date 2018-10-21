package com.example.nfnt.libraryfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by NFNT on 10/20/2018.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // langsung pindah ke MainActivity atau activity lain
        // begitu memasuki splash screen ini
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
