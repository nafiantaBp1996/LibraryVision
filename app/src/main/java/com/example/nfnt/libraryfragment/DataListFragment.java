package com.example.nfnt.libraryfragment;

/**
 * Created by NFNT on 9/17/2018.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataListFragment extends android.support.v4.app.ListFragment {

    static interface Listener{
        void itemClicked(long id,String url);
    }
    private Listener listener;

    public DataListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] names = new String[ImageReplacer.dataImage.length];
        for (int i=0; i<names.length; i++) {
            names[i] = ImageReplacer.dataImage[i].getNama();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);
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
            String url = "wira";
            listener.itemClicked(id,url);
        }
    }

}
