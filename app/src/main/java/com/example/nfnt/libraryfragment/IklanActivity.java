package com.example.nfnt.libraryfragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class IklanActivity extends AppCompatActivity {


    ListView listViewItem;
    VideoView videoViewItem;
    TextView loadingText;

    ArrayList<String> listVideoData;
    ArrayAdapter adapterData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iklan);

        videoViewItem = (VideoView) findViewById(R.id.videoViewRes);
        loadingText = (TextView) findViewById(R.id.text_loading);
        loadingText.setVisibility(View.INVISIBLE);
        Button close = (Button) findViewById(R.id.closeBtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //listVideoData = new ArrayList<>();
        //listVideoData.add("Jangan tonton Dosa");
        //listVideoData.add("Video Lucu");


                getMediaPlayer(getVideoFile("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"));
    }

    private Uri getVideoFile(String videoName)
    {
        if (URLUtil.isValidUrl(videoName))
        {
            Log.d("VIDEO",videoName);
            return Uri.parse(videoName);
        }
        else {
            Log.d("VIDEO","android.resource://" + getPackageName() + "/raw/" + videoName);
            return Uri.parse("android.resource://" + getPackageName() + "/raw/" + videoName);
        }
    }

    private void getMediaPlayer(Uri uri)
    {
        loadingText.setVisibility(View.VISIBLE);
        videoViewItem.setVideoURI(uri);
        videoViewItem.requestFocus();

        videoViewItem.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                loadingText.setVisibility(View.INVISIBLE);
                videoViewItem.start();
            }
        });

        videoViewItem.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(IklanActivity.this,"The End",Toast.LENGTH_SHORT).show();
                finish();
                videoViewItem.seekTo(0);
            }
        });
    }
}
