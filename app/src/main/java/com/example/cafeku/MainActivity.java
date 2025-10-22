package com.example.cafeku;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.example.cafeku.DAO.LevelDao;
import com.example.cafeku.DAO.PointDao;
import com.example.cafeku.database.LevelDatabase;
import com.example.cafeku.database.PointDatabase;
import com.example.cafeku.database.UserDatabase;
import com.example.cafeku.model.LevelModel;
import com.example.cafeku.model.Point;
import com.example.cafeku.model.User;
import com.google.android.material.button.MaterialButton;

import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;

public class MainActivity extends AppCompatActivity {

    static final int PERMISSION_REQUEST_CODE = 101;
    ArrayList<Integer> idList = new ArrayList<>();
    ArrayList<String> namaList = new ArrayList<>();
    ArrayList<String> deskripsiList = new ArrayList<>();
    ArrayList<Integer> hargaList = new ArrayList<>();
    ArrayList<String> gambarList = new ArrayList<>();

    ArrayList<Integer> point = new ArrayList<>();
    // Container 1
    ArrayList<Integer> idList2 = new ArrayList<>();
    ArrayList<String> namaList2 = new ArrayList<>();
    ArrayList<String> deskripsiList2 = new ArrayList<>();
    ArrayList<Integer> hargaList2 = new ArrayList<>();
    ArrayList<String> gambarList2 = new ArrayList<>();
    ArrayList<Integer> point2 = new ArrayList<>();

    // Container 2
    ArrayList<Integer> idList3 = new ArrayList<>();
    ArrayList<String> namaList3 = new ArrayList<>();
    ArrayList<String> deskripsiList3 = new ArrayList<>();
    ArrayList<Integer> hargaList3 = new ArrayList<>();
    ArrayList<String> gambarList3 = new ArrayList<>();
    ArrayList<Integer> point3 = new ArrayList<>();
    // Container 1
    ArrayList<Integer> idList4 = new ArrayList<>();
    ArrayList<String> namaList4 = new ArrayList<>();
    ArrayList<String> deskripsiList4 = new ArrayList<>();
    ArrayList<Integer> hargaList4 = new ArrayList<>();
    ArrayList<String> gambarList4 = new ArrayList<>();
    ArrayList<Integer> point4 = new ArrayList<>();

    ArrayList<Integer> idList5 = new ArrayList<>();
    ArrayList<String> namaList5 = new ArrayList<>();
    ArrayList<String> deskripsiList5 = new ArrayList<>();
    ArrayList<Integer> hargaList5 = new ArrayList<>();
    ArrayList<String> gambarList5 = new ArrayList<>();
    ArrayList<Integer> point5 = new ArrayList<>();

    ArrayList<Integer> idListS = new ArrayList<>();
    ArrayList<String> namaListS = new ArrayList<>();
    ArrayList<String> deskripsiListS = new ArrayList<>();
    ArrayList<Integer> hargaListS = new ArrayList<>();
    ArrayList<String> gambarListS = new ArrayList<>();
    ArrayList<Integer> pointS = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        new notificationHelper(getApplicationContext());


//        Integer list untuk menyimpan semua layout di laman cafeku seperti gambar,nama,harga dan container yang membungkus itu semua
        int[] parent1 = {R.id.katalog1, R.id.katalog2, R.id.katalog3, R.id.katalog4, R.id.katalog5};
        int[] imgIds1 = {R.id.imgMenu1, R.id.imgMenu2, R.id.imgMenu3, R.id.imgMenu4, R.id.imgMenu5};
        int[] txtIds1 = {R.id.txtMenu1, R.id.txtMenu2, R.id.txtMenu3, R.id.txtMenu4, R.id.txtMenu5};
        int[] btnIds1 = {R.id.btnMenu1, R.id.btnMenu2, R.id.btnMenu3, R.id.btnMenu4, R.id.btnMenu5};
        int[] parent2 = {R.id.katalog6, R.id.katalog7, R.id.katalog8, R.id.katalog9, R.id.katalog10};
        int[] imgIds2 = {R.id.imgMenu6, R.id.imgMenu7, R.id.imgMenu8, R.id.imgMenu9, R.id.imgMenu10};
        int[] txtIds2 = {R.id.txtMenu6, R.id.txtMenu7, R.id.txtMenu8, R.id.txtMenu9, R.id.txtMenu10};
        int[] btnIds2 = {R.id.btnMenu6, R.id.btnMenu7, R.id.btnMenu8, R.id.btnMenu9, R.id.btnMenu10};
        int[] parent3 = {R.id.katalog11, R.id.katalog12, R.id.katalog13, R.id.katalog14, R.id.katalog15};
        int[] imgIds3 = {R.id.imgMenu11, R.id.imgMenu12, R.id.imgMenu13, R.id.imgMenu14, R.id.imgMenu15};
        int[] txtIds3 = {R.id.txtMenu11, R.id.txtMenu12, R.id.txtMenu13, R.id.txtMenu14, R.id.txtMenu15};
        int[] btnIds3 = {R.id.btnMenu11, R.id.btnMenu12, R.id.btnMenu13, R.id.btnMenu14, R.id.btnMenu15};
        int[] parent4 = {R.id.katalog16, R.id.katalog17, R.id.katalog18, R.id.katalog19, R.id.katalog20};
        int[] imgIds4 = {R.id.imgMenu16, R.id.imgMenu17, R.id.imgMenu18, R.id.imgMenu19, R.id.imgMenu20};
        int[] txtIds4 = {R.id.txtMenu16, R.id.txtMenu17, R.id.txtMenu18, R.id.txtMenu19, R.id.txtMenu20};
        int[] btnIds4 = {R.id.btnMenu16, R.id.btnMenu17, R.id.btnMenu18, R.id.btnMenu19, R.id.btnMenu20};
        int[] parent5 = {R.id.katalog21, R.id.katalog22, R.id.katalog23, R.id.katalog24, R.id.katalog25};
        int[] imgIds5 = {R.id.imgMenu21, R.id.imgMenu22, R.id.imgMenu23, R.id.imgMenu24, R.id.imgMenu25};
        int[] txtIds5 = {R.id.txtMenu21, R.id.txtMenu22, R.id.txtMenu23, R.id.txtMenu24, R.id.txtMenu25};
        int[] btnIds5 = {R.id.btnMenu21, R.id.btnMenu22, R.id.btnMenu23, R.id.btnMenu24, R.id.btnMenu25};
        int[] parentS = {R.id.katalogs1, R.id.katalogs2, R.id.katalogs3, R.id.katalogs4, R.id.katalogs5};
        int[] imgIdsS = {R.id.imgMenus1, R.id.imgMenus2, R.id.imgMenus3, R.id.imgMenus4, R.id.imgMenus5};
        int[] txtIdsS = {R.id.txtMenus1, R.id.txtMenus2, R.id.txtMenus3, R.id.txtMenus4, R.id.txtMenus5};
        int[] btnIdsS = {R.id.btnMenus1, R.id.btnMenus2, R.id.btnMenus3, R.id.btnMenus4, R.id.btnMenus5};

//      fungsi untuk load data json berisi data dari setiap produk dan disimpan di array list untuk dikirim ke detail
        loadJsonToList("MenuSpecial.json", idListS, namaListS, deskripsiListS, hargaListS, gambarListS, pointS);
        loadJsonToList("datakatalog1.json", idList, namaList, deskripsiList, hargaList, gambarList, point);
        loadJsonToList("datakatalog2.json", idList2, namaList2, deskripsiList2, hargaList2, gambarList2, point2);
        loadJsonToList("datakatalog3.json", idList3, namaList3, deskripsiList3, hargaList3, gambarList3, point3);
        loadJsonToList("datakatalog4.json", idList4, namaList4, deskripsiList4, hargaList4, gambarList4, point4);
        loadJsonToList("datakatalog5.json", idList5, namaList5, deskripsiList5, hargaList5, gambarList5, point5);

//        fungsi untuk setup semua data ke layout
        setupProduk(parentS, imgIdsS, txtIdsS, btnIdsS, idListS, namaListS, deskripsiListS, hargaListS, gambarListS, pointS);
        setupProduk(parent1, imgIds1, txtIds1, btnIds1, idList, namaList, deskripsiList, hargaList, gambarList, point);
        setupProduk(parent2, imgIds2, txtIds2, btnIds2, idList2, namaList2, deskripsiList2, hargaList2, gambarList2, point2);
        setupProduk(parent3, imgIds3, txtIds3, btnIds3, idList3, namaList3, deskripsiList3, hargaList3, gambarList3, point3);
        setupProduk(parent4, imgIds4, txtIds4, btnIds4, idList4, namaList4, deskripsiList4, hargaList4, gambarList4, point4);
        setupProduk(parent5, imgIds5, txtIds5, btnIds5, idList5, namaList5, deskripsiList5, hargaList5, gambarList5, point5);

        TextView user = findViewById(R.id.usernamegreeting);
//Menngambil dari data base yang sudah disimpan  nama user setelah login
        User u = UserDatabase.getInstance(this).userDao().getUser();
        if(u != null){
            user.setText(u.username);
        }else{
           user.setText("Hello,Guest");
        }

//        setup rank user dengan nama dan progresnya
        ProgressBar pg = findViewById(R.id.progressBar);
        TextView rank = findViewById(R.id.homeranktxt);

        Point p = PointDatabase.getInstance(this).pointDao().getPoints();
        int userPoint = (p != null ? p.totalPoint : 0);

        LevelDao lvl = LevelDatabase.getInstance(this).levelDao();
        LevelModel l = lvl.select();

        if (l != null) {
            rank.setText(l.LevelName);
            int progress = 0;
// progres dimbil dari point user dikurangi jumlah point yang dibutuhkan saat ini dibagi point
// yang dibutuhkan untuk naik level dikurangi point yang dibuthkan dilevel ini lalu dikali 100 untuk mend
            switch (l.Level) {
                case 1:
                    progress = (int) (((float) (userPoint - 0) / (20 - 0)) * 100);
                    break;
                case 2:
                    progress = (int) (((float) (userPoint - 20) / (50 - 20)) * 100);
                    break;
                case 3:
                    progress = (int) (((float) (userPoint - 50) / (100 - 50)) * 100);
                    break;
                case 4:
                    progress = (int) (((float) (userPoint - 100) / (1000 - 100)) * 100);
                    break;
                case 5:
                    progress = 100;
                    break;
                default:
                    progress = 0;
                    break;
            }

            // Batasi agar progress selalu di antara 0–100
            if (progress < 0) progress = 0;
            if (progress > 100) progress = 100;

            pg.setProgress(progress);

        } else {
            rank.setText("Keren");
            pg.setProgress(0);
        }

        rank.setOnClickListener(v->{
            Intent irank = new Intent(MainActivity.this,RankActivity.class);
            startActivity(irank);
        });


//Greeting Berdasarkan Waktu
        TextView greetingText1 = findViewById(R.id.greetingtext);
        TextView greetingtext2 = findViewById(R.id.greetingtext2);
        TextView greetingtext3 = findViewById(R.id.greetingtext3);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting1, greeting2, greeting3;
        if (hour >= 5 && hour < 11) {
            greeting1 = "☀️ Pagi! Saatnya roti dan kopi hangat.";
            greeting2 = "🌤️ Nikmati pagi dengan kopi favoritmu.";
            greeting3 = "🍞 Sarapan enak, hari pun semangat!";
        } else if (hour >= 11 && hour < 15) {
            greeting1 = "🥤 Siang panas, enaknya minum yang dingin.";
            greeting2 = "☕ Waktunya es kopi nyegerin siangmu.";
            greeting3 = "🍰 Siang santai, dessert manis siap temani.";
        } else if (hour >= 15 && hour < 19) {
            greeting1 = "🌆 Sore adem, pas buat nongkrong di kafe.";
            greeting2 = "☕ Nyunset bareng kopi, nikmat banget.";
            greeting3 = "🍪 Sore manis ditemani camilan lezat.";
        } else {
            greeting1 = "🌙 Malam tenang, nikmati kopi sebelum tidur.";
            greeting2 = "✨ Santai malam ditemani aroma kopi.";
            greeting3 = "🌃 Waktu pas buat ngopi dan rebahan.";

        }
        greetingText1.setText(greeting1);
        greetingtext2.setText(greeting2);
        greetingtext3.setText(greeting3);


        //Image Slider
        ImageSlider imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.homeimg, "CAFEKU", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_2, "Clean Service", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_3, "High Quality", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_4, "Make Your Day", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imageList);

        //Move Event
        int[] imageIds = {R.id.vcr1, R.id.vcr2, R.id.vcr3, R.id.vcr4, R.id.nvVoucher, R.id.nvcart, R.id.nvProfile};
        Class<?>[] destinations = {
                VoucherActivity.class,
                VoucherActivity.class,
                VoucherActivity.class,
                VoucherActivity.class,
                VoucherActivity.class,
                CartActivity.class,
                Profile.class
        };
        movepage(imageIds, destinations);

    }

    //Functions
    private void movepage(int[] image, Class<?>[] destination) {
        for (int i = 0; i < image.length; i++) {

            ImageView imageclick = findViewById(image[i]);
            Class<?> destinationfinal = destination[i];

            imageclick.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, destinationfinal);
                startActivity(intent);
            });
        }
    }

    private void setupProduk(int[] katalogId, int[] imgId, int[] txtId, int[] btnId,
                             ArrayList<Integer> idList, ArrayList<String> namaList,
                             ArrayList<String> deskripsiList, ArrayList<Integer> hargaList,
                             ArrayList<String> gambarList, ArrayList<Integer> point) {
        for (int i = 0; i < namaList.size(); i++) {
            int index = i;
            ImageView img = findViewById(imgId[i])    ;
            TextView txt = findViewById(txtId[i]);
            MaterialButton btn = findViewById(btnId[i]);
            LinearLayout katalog = findViewById(katalogId[i]);

            // Format harga jadi Rupiah
            NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String harga = rupiah.format(hargaList.get(i));

            // Set data dari JSON
            txt.setText(namaList.get(i));
            btn.setText(harga);

            try {
                // Akses file dari folder assets
                InputStream inputStream = getAssets().open("images/" + gambarList.get(i) + ".png");

                // Ubah jadi Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // Tampilkan di ImageView
                img.setImageBitmap(bitmap);

                // Tutup stream
                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            // Klik → pindah ke DetailActivity
            katalog.setOnClickListener(v -> {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("id", idList.get(index));
                intent.putExtra("name", namaList.get(index));
                intent.putExtra("deskripsi", deskripsiList.get(index));
                intent.putExtra("harga", hargaList.get(index));
                intent.putExtra("gambar", "images/" + gambarList.get(index) + ".png");
                intent.putExtra("point", point.get(index));
                startActivity(intent);
            });
        }
    }

    private void loadJsonToList(String fileName,
                                ArrayList<Integer> idList,
                                ArrayList<String> namaList,
                                ArrayList<String> deskripsiList,
                                ArrayList<Integer> hargaList,
                                ArrayList<String> gambarList,
                                ArrayList<Integer> point) {
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();       // Mengecek ukuran file (dalam byte)
            byte[] buffer = new byte[size];  // Membuat array byte sebesar ukuran file
            is.read(buffer);                 // Membaca seluruh isi file ke array
            is.close();                      // Menutup file agar tidak terjadi memory leak

//Ubah byte menjadi teks JSON Mengonversi byte array menjadi String (teks JSON utuh) dengan encoding UTF-8.
            String json = new String(buffer, "UTF-8");
//            Membuat objek JSONArray
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
//                Mengambil satu objek produk dari JSON dengan (i)
                JSONObject obj = jsonArray.getJSONObject(i);

                idList.add(obj.getInt("id"));
                namaList.add(obj.getString("nama"));
                deskripsiList.add(obj.getString("deskripsi"));
                hargaList.add(obj.getInt("harga"));
                gambarList.add(obj.getString("gambar"));
                point.add(obj.getInt("point"));
            }
//            Menangani error
        } catch (Exception e) {
            Log.e("JSON", "Error loading JSON file " + fileName, e);
            e.printStackTrace();
        }
    }





}



