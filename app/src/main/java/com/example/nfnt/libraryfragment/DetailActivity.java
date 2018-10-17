package com.example.nfnt.libraryfragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        NabiDetailFragment frag = (NabiDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFrag);
        Bundle b = getIntent().getExtras();
        frag.setResep(b.getLong("id"));
        //frag.setResep(1);
    }
}
