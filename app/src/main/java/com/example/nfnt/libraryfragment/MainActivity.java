package com.example.nfnt.libraryfragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements DataListFragment.Listener {
    private FirebaseDatabase database;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //wira
        //mencegah internetexcepetion
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("ImageSwap");
        //syafri

        MyFirebaseIdService news = new MyFirebaseIdService();
        news.showToken();
        //intent yang didapat dari displaynotification
        Intent ahay = getIntent();
        String pesan = ahay.getStringExtra("isi");
        String pengirim = ahay.getStringExtra("judul");
        String gambar = ahay.getStringExtra("gambar");
        String nama = ahay.getStringExtra("nama");

        writeNewGambar("data "+nama,nama,gambar);
    }

    //wira
    @Override
    public void onResume(){
        super.onResume();

        //syntax dibawah ini berfungsi untuk mengambil nilai putextra dari class MyNotificationManager
        //untuk selanjutnya di tempatkan di objek yang ada di layout activity_pesan


    }

    //syafri

//    public void onShowDetails(View view){
//        Intent intent = new Intent(this, DetailActivity.class);
//        startActivity(intent);
//    }

    public void itemClicked(long id,String url)
    {
        View fragmenDetail = findViewById(R.id.detailingFrag);
        if(fragmenDetail!=null)
        {

            DetailFragment deta = new DetailFragment();

            FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
            deta.setImgs(id);
            deta.seturl(url);
            fragTrans.replace(R.id.detailingFrag,deta);
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //fragTrans.addToBackStack(null);
            fragTrans.commit();
        }

        else
        {
            Toast.makeText(this, "Item " + id + " was clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getApplicationContext(), DetailActivity.class);
            Bundle b = new Bundle();
            b.putString("url",url);
            b.putLong("id",id);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
    private void writeNewGambar(String userId, String name, String url) {
        Gambar yeye = new Gambar(name, url);

        mDatabase.child("").child(userId).setValue(yeye);
    }
}
