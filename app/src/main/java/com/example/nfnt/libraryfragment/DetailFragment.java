package com.example.nfnt.libraryfragment;

/**
 * Created by NFNT on 9/17/2018.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private long idImg;
    ImageView image, image2;
    private static final int SELECT_PICTURE = 1;
    public String selectedImagePath = "";
    Bitmap imgFileCOre,imgFileRepalcer;
    int actResult =0;

    public DetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_maina, container, false);
    }

    public void setImgs(long id){
        this.idImg = id;
    }

    public Bitmap statisImg (int draw)
    {
        Bitmap img = BitmapFactory.decodeResource(getActivity().getResources(),draw);
        return  img;
    }

    @Override
    public void onStart() {

        super.onStart();
        View view = getView();
        if (view != null) {

            image = (ImageView) view.findViewById(R.id.imageLayout);
            image2 = (ImageView) view.findViewById(R.id.imageLayout2);
            Button proses = (Button) view.findViewById(R.id.btnEmoji);

            ImageReplacer data = ImageReplacer.dataImage[(int)idImg];
            imgFileRepalcer = statisImg(data.getGambar_());
            image2.setImageBitmap(imgFileRepalcer);
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
                    if(imgFileCOre==null)
                        {
                            Toast.makeText(v.getContext(),"Tidak Ada Gambar Yang Akan di Replace",Toast.LENGTH_SHORT).show();
                        }
                    else
                        {
                            EmojifierMadeByNafi emoji = new EmojifierMadeByNafi();
                            image.setImageBitmap(emoji.detectFaces(getContext(),imgFileCOre,imgFileRepalcer));
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
                    imgFileCOre = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

