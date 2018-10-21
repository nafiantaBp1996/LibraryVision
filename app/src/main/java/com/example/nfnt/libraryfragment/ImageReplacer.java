package com.example.nfnt.libraryfragment;

/**
 * Created by NFNT on 9/17/2018.
 */

public class ImageReplacer {
    private String nama;
    private int gambar_;
    public static final ImageReplacer[] dataImage = {
            new ImageReplacer("1. DOG", R.drawable.dog),
            new ImageReplacer("2. COW", R.drawable.cow),
            new ImageReplacer("3. SMILE", R.drawable.smile),
            new ImageReplacer("4. CLOSE SMILE", R.drawable.closesmile),
            new ImageReplacer("5. FROWN", R.drawable.frown),
            new ImageReplacer("6. LEFT WINK", R.drawable.leftwink)

    };

    private ImageReplacer(String name, int gambar) {
        this.nama = name;
        this.gambar_ = gambar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getGambar_() {
        return gambar_;
    }

    public void setGambar_(int gambar_) {
        this.gambar_ = gambar_;
    }
}
