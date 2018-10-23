package com.example.nfnt.libraryfragment;

public class Gambar {

    public String nmFile;
    public String url;

    public static Gambar [] dataGambar;
    public Gambar() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Gambar(String nmFile, String url) {
        this.nmFile = nmFile;
        this.url = url;
    }

    public String getNmFile() {
        return nmFile;
    }

    public void setNmFile(String nmFile) {
        this.nmFile = nmFile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}