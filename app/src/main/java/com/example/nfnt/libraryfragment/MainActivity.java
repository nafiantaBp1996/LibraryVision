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

public class MainActivity extends AppCompatActivity {
    ImageView image;
    private static final int SELECT_PICTURE = 1;
    public String selectedImagePath = "";
    Bitmap imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.imageLayout);
        image.setImageResource(R.drawable.original);

    }

    public void prosesImage(View v)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap newBitmap = imgFile;

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
        Bitmap newBitmap = imgFile;
        Emojifier emoji = new Emojifier();
        image.setImageBitmap(emoji.detectFaces(getApplicationContext(),newBitmap));
    }

    public void getGambar(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                image.setVisibility(View.VISIBLE);
                image.setImageURI(selectedImageUri);
                try {
                    imgFile = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this,selectedImageUri.getEncodedPath(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
