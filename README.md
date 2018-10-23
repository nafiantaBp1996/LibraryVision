# Image Replacer
| Nama | Absen |
| :---------------: | :---------------:|
| Nafianta Budi P.  | 20 |
| Syafri Wira W.    | 22 |

## Image Replacer
Image replacer adalah aplikasi pengganti wajah menggunakan sekumpulan gambar yang dinamis

## Android OS dan Level
min Sdk version yang digunakan adalah 19

## List class dan deskripsi singkat
+ DataListFragment.java
    + Kelas ini berfungsi menghandle listview dari kelas object statis yakni ImageReplacer.java
+ DetailActivity.java
    + Kelas yang berfungsi untuk memparsing data dari List activity untuk di proses pada detail fragment.
+ DetailFragment.java
    + Fungsi utama aplikasi ini berada pada kelas ini, yakni proses penggantian gambar pilihan user dengan gambar emojifier dan mengambil data sesuai list view yang ada
+ DisplayNotification.java
    + Kelas ini berfungsi untuk membuat notification builder ketika data JSON dikirim, klass ini dijalankan saat ada pesan masuk
+ EmojifierMadeByNafi.java
    + Kelas ini memiliki 2 fungsi yakni deteksi wajah dan pengganti wajah yang terdeteksi dengan gambar dengan parsing gambar dinamis sesuai pilihannya 
+ Gambar.java
    + kelas ini digunakan untuk menghandle objek gambar dari firebase database
+ ImageReplacer.java
    + Merupakan kelas objek imagereplacer statis untuk diproses pada fungsi replace image 
+ ItemFragment.java
    + Kelas ini berfungsi untuk menghandle Listview dinamis dari firebase 
+ MainActivity.java
    + Tampilan awal yang menjalankan fragment berupa listview dengan title image replacer yang akan dipilih da
+ MyFirebaseIdService.java
    + kelas yang digunakan untuk mendapatkan token aplikasi 
+ MyFirebaseMessage.java
    + Kelas ini berfungsi untum mengambil data JSON untuk selanjutnya diparsing ke kelas DisplayNotificatiion 
+ SplashActivity.java
    + kelas pertama yang langsung mengarahkan activity ke MainActivity

## Lisensi

