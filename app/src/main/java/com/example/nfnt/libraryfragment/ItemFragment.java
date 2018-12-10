package com.example.nfnt.libraryfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ItemFragment extends android.support.v4.app.ListFragment {
    private ArrayList<String> gambarname = new ArrayList<>();
    private ArrayList<String> urlname = new ArrayList<>();
    private FirebaseDatabase database;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;

    interface Listener{
        void itemClicked(long id,String url);
    }
    private Listener listener;

    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageSwap/");
        mStorageRef = FirebaseStorage.getInstance().getReference();
//        String[] names = new String[ImageReplacer.dataImage.length];
//        for (int i=0; i<names.length; i++) {
//            names[i] = ImageReplacer.dataImage[i].getNama();
//        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_1, gambarname);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_1, urlname);
        setListAdapter(adapter);
        mDatabase.child("ImageSwap").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                urlname.clear();
                gambarname.clear();
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    String val = data.child("/url").getValue().toString();
                    String name = data.child("/nmFile").getValue().toString();

                    urlname.add(val);
                    gambarname.add(name);
//                    dataurl.add(val);
                }

                adapter.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);

    }
    @ Override
    public void onAttach ( Context context ) {
        super . onAttach ( context );
        this . listener = (Listener) context ;
    }
    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id){
        if(listener != null)
        {
            String url=urlname.get(position);
            Toast.makeText(itemView.getContext(),urlname.get(position),Toast.LENGTH_SHORT).show();
            listener.itemClicked(id,url);
        }
    }


}