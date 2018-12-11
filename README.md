# Image Replacer
| Group | 06 |
| :---------------: | :---------------:|
| Nafianta Budi P.  | 20 |
| Syafri Wira W.    | 22 |

## Image Replacer
Image replacer adalah aplikasi pengganti wajah menggunakan sekumpulan gambar yang dinamis

## Screenshots
+ layar home (listview dinamis dari database firebase)
![Layar Home](https://i.ibb.co/wKNdTsn/Screenshot-20181211-230421.jpg)

+ Replace wajah
![Layar Replacer](https://i.ibb.co/R7DFthf/Screenshot-20181211-221056.jpg)

+ iklan (terdapat tombol close disebelah kiri atas untuk menutup iklan)
![Layar Iklan](https://i.ibb.co/LSC2Ys7/Screenshot-20181211-221021.jpg)

+ widget (update widget ketika telah mereplace image)
![widget](https://i.ibb.co/sRgyGFx/Whats-App-Image-2018-12-11-at-23-16-50.jpg)

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
+ Emojifier.java
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
+ IklanActivity.java
    + kelas yang digunakan untuk menampilkan iklan2an berupa video
 + WidgetIcon.java
    + kelas Widget yang berisi function untuk memanipulasi widget

## Referensi
[widget](https://developer.android.com/guide/topics/appwidgets/)
## Lisensi
The MIT License

Copyright (c) 2010-2018 Google, Inc. http://angularjs.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

      

