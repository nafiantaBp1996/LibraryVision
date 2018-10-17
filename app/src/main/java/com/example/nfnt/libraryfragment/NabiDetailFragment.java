package com.example.nfnt.libraryfragment;

/**
 * Created by NFNT on 9/17/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NabiDetailFragment extends Fragment {

    private long nabiId;

    public NabiDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_nabi_detail, container, false);
    }

    public void setResep(long id){
        this.nabiId = id;
    }

    @Override
    public void onStart() {

        super.onStart();
        View view = getView();
        if (view != null) {
            TextView tittle = (TextView) view.findViewById(R.id.textJudul);
            Nabi resep = Nabi.kisahnabi[(int) nabiId];
            tittle.setText(resep.getNama_nabi());
            TextView detail = (TextView) view.findViewById(R.id.textDetail);
            detail.setText(resep.getDeskripsi());
            ImageView gambar = (ImageView) view.findViewById(R.id.gamabar_resep);
            gambar.setImageResource(resep.getGambar_());
        }
    }

}
