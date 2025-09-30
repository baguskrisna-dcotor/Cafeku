package com.example.cafeku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.nio.channels.AsynchronousFileChannel;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> idList = new ArrayList<>();
    ArrayList<String> namaList = new ArrayList<>();
    ArrayList<String> deskripsiList = new ArrayList<>();
    ArrayList<Integer> hargaList = new ArrayList<>();
    ArrayList<String> gambarList = new ArrayList<>();
    // Container 1
    ArrayList<Integer> idList2 = new ArrayList<>();
    ArrayList<String> namaList2 = new ArrayList<>();
    ArrayList<String> deskripsiList2 = new ArrayList<>();
    ArrayList<Integer> hargaList2 = new ArrayList<>();
    ArrayList<String> gambarList2 = new ArrayList<>();

    // Container 2
    ArrayList<Integer> idList3 = new ArrayList<>();
    ArrayList<String> namaList3 = new ArrayList<>();
    ArrayList<String> deskripsiList3 = new ArrayList<>();
    ArrayList<Integer> hargaList3 = new ArrayList<>();
    ArrayList<String> gambarList3 = new ArrayList<>();
    // Container 1
    ArrayList<Integer> idList4 = new ArrayList<>();
    ArrayList<String> namaList4 = new ArrayList<>();
    ArrayList<String> deskripsiList4 = new ArrayList<>();
    ArrayList<Integer> hargaList4 = new ArrayList<>();
    ArrayList<String> gambarList4 = new ArrayList<>();

    // Container 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadJsonToList("datakatalog1.json", idList, namaList, deskripsiList, hargaList, gambarList);
        loadJsonToList("datakatalog2.json", idList2, namaList2, deskripsiList2, hargaList2, gambarList2);
        loadJsonToList("datakatalog3.json", idList3, namaList3, deskripsiList3, hargaList3, gambarList3);
        loadJsonToList("datakatalog4.json", idList4, namaList4, deskripsiList4, hargaList4, gambarList4);

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

        setupProduk(parent1, imgIds1, txtIds1, btnIds1, idList, namaList, deskripsiList, hargaList, gambarList);
        setupProduk(parent2, imgIds2, txtIds2, btnIds2, idList2, namaList2, deskripsiList2, hargaList2, gambarList2);
        setupProduk(parent3, imgIds3, txtIds3, btnIds3, idList3, namaList3, deskripsiList3, hargaList3, gambarList3);
        setupProduk(parent4, imgIds4, txtIds4, btnIds4, idList4, namaList4, deskripsiList4, hargaList4, gambarList4);


        TextView greetingText1 = findViewById(R.id.greetingtext);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;
        if (hour >= 5 && hour < 11) {
            greeting = "☀️ Jam segini enak nih roti buat nyarap pagi";
        } else if (hour >= 11 && hour < 15) {
            greeting = "🥵 Siang siang gini mending nyari yang dingin nyegerin.";
        } else if (hour >= 15 && hour < 19) {
            greeting = "🌆 Nyunset bareng di cafeku yukk";
        } else {
            greeting = "🌙 Nikmati waktu istirahat dengan menu favoritmu";
        }
        greetingText1.setText(greeting);

        ImageSlider imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();

        imageList.add(new SlideModel(R.drawable.homeimg, "homeimg1", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_2, "homeimg2", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_3, "homeimg3", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_4, "homeimg4", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(imageList);
}

    private void  setupProduk(int[] katalogId, int[] imgId, int[] txtId, int[] btnId,
                              ArrayList<Integer> idList, ArrayList<String> namaList,
                              ArrayList<String> deskripsiList, ArrayList<Integer> hargaList,
                              ArrayList<String> gambarList)
    {
        for (int i = 0; i < namaList.size(); i++) {
            int index = i;
            ImageView img = findViewById(imgId[i]);
            TextView txt = findViewById(txtId[i]);
            MaterialButton btn = findViewById(btnId[i]);
            LinearLayout katalog = findViewById(katalogId[i]);

            // Format harga jadi Rupiah
            NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String harga = rupiah.format(hargaList.get(i));

            // Set data dari JSON
            txt.setText(namaList.get(i));
            btn.setText(harga);

            int resId = getResources().getIdentifier(gambarList.get(i), "drawable", getPackageName());
            img.setImageResource(resId);

            // Klik → pindah ke DetailActivity
            katalog.setOnClickListener(v -> {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("id", idList.get(index));
                intent.putExtra("name", namaList.get(index));
                intent.putExtra("deskripsi", deskripsiList.get(index));
                intent.putExtra("harga", hargaList.get(index));
                intent.putExtra("gambar", gambarList.get(index));
                startActivity(intent);
            });
        }
    }

    private void loadJsonToList(String fileName,
                                ArrayList<Integer> idList,
                                ArrayList<String> namaList,
                                ArrayList<String> deskripsiList,
                                ArrayList<Integer> hargaList,
                                ArrayList<String> gambarList) {
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                idList.add(obj.getInt("id"));
                namaList.add(obj.getString("nama"));
                deskripsiList.add(obj.getString("deskripsi"));
                hargaList.add(obj.getInt("harga"));
                gambarList.add(obj.getString("gambar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}



