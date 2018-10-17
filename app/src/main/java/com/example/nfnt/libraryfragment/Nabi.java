package com.example.nfnt.libraryfragment;

/**
 * Created by NFNT on 9/17/2018.
 */

public class Nabi {
    private String nama_nabi,deskripsi;
    private int gambar_;
    public static final Nabi[]kisahnabi={
            new Nabi("1. Nabi Adam","Nabi pertama " +
                    "\nAllah Subhanahu wa Ta’ala memberitahukan kepada para malaikat tentang penciptaan Adam ‘alaihis salam, Dia berfirman:\n" +
                    "\n" +
                    "“Sesungguhnya aku hendak menjadikan seorang khalifah di muka bumi.” (QS. Al Baqarah: 30)\n" +
                    "\n" +
                    "Yakni makhluk yang satu dengan yang lain saling menggantikan. Demikianlah Allah Subhanahu wa Ta’ala memberitahukan kepada para malaikat tentang penciptaan Adam sebagaimana Dia memberitahukan perkara besar sebelum terwujud.\n" +
                    "\n" +
                    "\n" +
                    "\n" ,R.drawable.sasd)
    };

    private Nabi(String name, String description,int gambar) {
        this.nama_nabi = name;
        this.deskripsi = description;
        this.gambar_ = gambar;
    }

    public String getNama_nabi() {
        return nama_nabi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
    public int getGambar_() {
        return gambar_;
    }
    public String toString(){
        return this.nama_nabi;
    }
}
