package com.example.nfnt.libraryfragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.IOException;

public class MainActi extends AppCompatActivity {
    ImageView image,image2;
    private static final int SELECT_PICTURE = 1;
    public String selectedImagePath = "";
    Bitmap imgFileCOre,imgFileRepalcer;
    int actResult =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maina);
        image = (ImageView) findViewById(R.id.imageLayout);
        image2 = (ImageView) findViewById(R.id.imageLayout2);

    }

    public void prosesImage(View v)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap newBitmap = imgFileCOre;

        Paint myRectPaint = new Paint();
        myRectPaint.setStrokeWidth(10);
        myRectPaint.setColor(Color.GREEN);
        myRectPaint.setStyle(Paint.Style.STROKE);

        FaceDetector facedetect = new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).build();
        if(!facedetect.isOperational()){
             new AlertDialog.Builder(v.getContext()).setMessage("Could not set up the face detector!").show();
            return;
         }

        Frame frame = new Frame.Builder().setBitmap(newBitmap).build();
        SparseArray<Face> faces = facedetect.detect(frame);
        Bitmap tempBitmap = Bitmap.createBitmap(newBitmap.getWidth(), newBitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(newBitmap, 0, 0, null);

         for(int i=0; i<faces.size(); i++) {
             Face thisFace = faces.valueAt(i);
             float x1 = thisFace.getPosition().x;
             float y1 = thisFace.getPosition().y;
             float x2 = x1 + thisFace.getWidth();
             float y2 = y1 + thisFace.getHeight();
             tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
         }
        image.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));

    }
    public void prosesEmoji(View v)
    {
//        NotificationCompat.Builder notifBuild = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext()).setSmallIcon(R.drawable.frown).setContentTitle("dasdasd").setContentText("sdsadas");
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(0, notifBuild.build());
// notificationId is a unique int for each notification that you must define

        Bitmap newBitmap = imgFileCOre;
        Bitmap emojier = imgFileRepalcer;
        EmojifierMadeByNafi emoji = new EmojifierMadeByNafi();
        //image.setImageBitmap(emoji.detectFaces(getApplicationContext(),newBitmap));
        image.setImageBitmap(emoji.detectFaces(getApplicationContext(),newBitmap,emojier));
    }


    public void getGambar(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        actResult = 1;
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }
    public void getGambars(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult();
        actResult = 2;
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                if(actResult==1)
                {
                    Uri selectedImageUri = data.getData();
                    image.setVisibility(View.VISIBLE);
                    image.setImageURI(selectedImageUri);
                    try {
                        imgFileCOre = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this,selectedImageUri.getEncodedPath(),Toast.LENGTH_SHORT).show();
                }
                else if(actResult==2)
                {
                    Uri selectedImageUri = data.getData();
                    image2.setVisibility(View.VISIBLE);
                    image2.setImageURI(selectedImageUri);
                    try {
                        imgFileRepalcer = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this,selectedImageUri.getEncodedPath(),Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
