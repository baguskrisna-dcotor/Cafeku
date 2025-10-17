package com.example.cafeku;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemChangeListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cafeku.DAO.LevelDao;
import com.example.cafeku.DAO.PointDao;
import com.example.cafeku.DAO.UserDao;
import com.example.cafeku.database.LevelDatabase;
import com.example.cafeku.database.UserDatabase;
import com.example.cafeku.model.LevelModel;
import com.example.cafeku.model.Point;
import com.example.cafeku.database.PointDatabase;
import com.example.cafeku.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ImageView btnMore,tvgender;
    private TextView tvusername,g1,g2,point;
    private ImageSlider slider;
    ArrayList<String> namaList1 = new ArrayList<>();
    ArrayList<SlideModel> slideModels = new ArrayList<>();
    ArrayList<String> title1 = new ArrayList<>();
    ArrayList<String> title2 = new ArrayList<>();
    ArrayList<String> namalvl = new ArrayList<>();
    ArrayList<Integer> lvl = new ArrayList<>();
    ArrayList<Integer> minpoint = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        double latitude = -7.797068;
        double longitude = 110.370529;

        tvusername = findViewById(R.id.nameUser);
        btnMore = findViewById(R.id.settingbutton);
        slider = findViewById(R.id.imageslider);
        g1 =findViewById(R.id.greetingtext1);
        g2 = findViewById(R.id.greetingtext2);
        point = findViewById(R.id.point);
        tvgender = findViewById(R.id.gender);

        LevelHandler(namalvl,lvl,minpoint);

        try {
            // 🔹 Baca JSON dari assets
            InputStream is = getAssets().open("slider.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            // 🔹 Parse JSON
            JSONObject obj = new JSONObject(json);
            JSONArray slides = obj.getJSONArray("slides");

            for (int i = 0; i < slides.length(); i++) {
                JSONObject item = slides.getJSONObject(i);
                String image = item.getString("image");
                String title = item.getString("title");
                String title_2 = item.getString("title2");

                slideModels.add(new SlideModel("file:///android_asset/images/" + image, ScaleTypes.FIT));
                title1.add(title);
                title2.add(title_2);
            }


            slider.setImageList(slideModels);

            slider.setItemChangeListener(new ItemChangeListener() {
                @Override
                public void onItemChanged(int i) {
                    if (i < title1.size()) {
                        g1.setText(title1.get(i));
                        g2.setText(title2.get(i));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent i = getIntent();
        String nama = i.getStringExtra("username");
        Boolean sex = i.getBooleanExtra("gender",false);
        UserDao n = UserDatabase.getInstance(this).userDao();

        User user = n.getUser(nama);
        if (user != null) {
            tvusername.setText(user.username);
            if (user.gender) {
                tvgender.setImageResource(R.drawable.male);
            } else {
                tvgender.setImageResource(R.drawable.remale);
            }
        } else {
            tvusername.setText("Halo, Guest");
        }

        //POIntttttttttt
        Point p = PointDatabase.getInstance(this).pointDao().getPoints();
        point.setText(String.valueOf(p !=null ? p.totalPoint       :0));


        btnMore.setOnClickListener(v -> showPopupMenu(v));
        loadJsonToList("datakatalog1.json", namaList1);
        loadJsonToList("datakatalog2.json", namaList1);
        loadJsonToList("datakatalog3.json", namaList1);
        loadJsonToList("datakatalog4.json", namaList1);
        loadJsonToList("datakatalog5.json", namaList1);

        int[] name = {
                R.id.txt1,
                R.id.txt2,
                R.id.txt3,
                R.id.txt4,
                R.id.txt5,
                R.id.txt6,
                R.id.txt7,
        };
        int[] imgIds1 = {
                R.id.image1,
                R.id.image2,
                R.id.image3,
                R.id.image4,
                R.id.image5,
                R.id.image6,
                R.id.image7
        };
        String[] Imglist = {
                "menu_1.png",
                "menu_2.png",
                "menu_3.png",
                "menu_4.png",
                "menu_5.png",
                "menu_6.png",
                "menu_7.png",
        };

        setupimage(imgIds1, Imglist, name, namaList1);

        int[] movebtn = {
                R.id.btnhome,
                R.id.btnVoucher,
                R.id.btnkeranjang,
                R.id.btnvoucher,
               };
        Class[] moveto = {
                MainActivity.class,
                VoucherActivity.class,
                CartActivity.class,
                VoucherActivity.class};
        movePage(movebtn, moveto);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Lokasi Cafe
        LatLng cafeLocation = new LatLng(-7.555600, 110.801003);
        mMap.addMarker(new MarkerOptions().position(cafeLocation).title("Cafeku SMKN 2 Surakarta"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cafeLocation, 15));
    }

    private void movePage(int[] buttonIds, Class<?>[] destinations) {
        for (int i = 0; i < buttonIds.length; i++) {
            View view = findViewById(buttonIds[i]);
            Class<?> targetActivity = destinations[i];

            if (view == null) continue; // amanin biar gak null pointer

            view.setOnClickListener(v -> {
                Intent intent = new Intent(Profile.this, targetActivity);
                startActivity(intent);
            });
        }
    }

    private void setupimage(int[] Images, String[] imgs, int[] names, ArrayList<String> name) {

        for (int i = 0; i < Images.length; i++) {
            int index = i;

            ImageView img = findViewById(Images[index]);
            TextView txt = findViewById(names[index]);
            txt.setText(name.get(index));
            try {
                // Akses file dari folder assets
                InputStream inputStream = getAssets().open("images/" + imgs[index]);

                // Ubah jadi Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // Tampilkan di ImageView
                img.setImageBitmap(bitmap);

                // Tutup stream
                inputStream.close();

            } catch (
                    IOException e) {
                e.printStackTrace();
            }


        }
    }


    private void showPopupMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenuInflater().inflate(R.menu.profile_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            Intent i = getIntent();
            String nama = i.getStringExtra("username");

            UserDatabase db = UserDatabase.getInstance(this);
            UserDao userdao = db.userDao();
            User exist = (nama != null) ? userdao.getUser(nama) : null;

            if (id == R.id.menu_edit_profile) {
                showEditProfileDialog();
                return true;

            } else if (id == R.id.menu_login) {
                if (exist == null) {
                    Toast.makeText(this, "Silakan login dulu", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Profile.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Kamu sudah login", Toast.LENGTH_SHORT).show();
                }
                return true;

            } else if (id == R.id.menu_logout) {
                if (exist != null) {
                    userdao.Logout(exist); // pastikan DAO kamu punya method ini
                    Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Kamu belum login", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            return false;
        });

        popup.show();
    }


    private void showEditProfileDialog() {
        // Inflate layout custom
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_edit_profile, null);

        EditText etName = view.findViewById(R.id.et_name);
        LinearLayout btnSave = view.findViewById(R.id.btn_save);
        ImageView btnClose = view.findViewById(R.id.btn_close);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false) // agar tidak bisa dismiss sembarangan
                .create();

        // klik tombol Save
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            // update profile di halaman utama
            tvusername.setText(name);

            Toast.makeText(this, "Data tersimpan!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        // klik tombol X (close)
        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void loadJsonToList(String fileName,
                                ArrayList<String> namaList) {
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
                namaList.add(obj.getString("nama"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void LevelHandler(
            ArrayList<String> namaLevel,
            ArrayList<Integer> level,
            ArrayList<Integer> minPoint
    ) {
        LevelDatabase lvldb = LevelDatabase.getInstance(this);
        LevelDao lvldao = lvldb.levelDao();
        Point p = PointDatabase.getInstance(this).pointDao().getPoints();
        int userPoint = (p != null ? p.totalPoint : 0);

        JSONArray jsonArray = null;

        try {
            InputStream is = getAssets().open("Level.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
           jsonArray = new JSONArray(json);


            namaLevel.clear();
            level.clear();
            minPoint.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                minPoint.add(obj.getInt("Pointmin"));
                namaLevel.add(obj.getString("nama"));
                level.add(obj.getInt("level"));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return ;
        }


        int achievedIndex = -1;
        for (int i = 0; i < minPoint.size(); i++) {
            if (userPoint >= minPoint.get(i)) {
                achievedIndex = i;
            }
        }

        if (achievedIndex != -1) {
            String currentLevelName = namaLevel.get(achievedIndex);
            int currentLevel = level.get(achievedIndex);
            int requiredPoint = minPoint.get(achievedIndex);

            LevelModel exist = lvldao.getLevelById(currentLevel);
            if (exist == null) {
                lvldao.insert(new LevelModel(currentLevel, currentLevelName, requiredPoint));
            }

            TextView tvLevelname = findViewById(R.id.levelname);
            TextView tvMinPoint = findViewById(R.id.minpoint);
            TextView tvlevel = findViewById(R.id.lvlnow);
            TextView poinuser = findViewById(R.id.pointnow);
            ImageView img = findViewById(R.id.imagelevel);

            int nextMinPoint;

            if (achievedIndex + 1 < minPoint.size()) {
                nextMinPoint = minPoint.get(achievedIndex + 1);
            } else {

                nextMinPoint = minPoint.get(achievedIndex);
            }


            String imageName = "image_level" + (achievedIndex + 1);
            int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (resId != 0) {
                img.setImageResource(resId);
            } else {
                img.setImageResource(R.drawable.dummy); // fallback aman
            }


            tvLevelname.setText(currentLevelName);
            poinuser.setText(String.valueOf(userPoint));
            tvlevel.setText("Level " + currentLevel);

            String txtmin = String.valueOf(nextMinPoint);
            tvMinPoint.setText("/" + txtmin);

            Log.d("LevelHandler", "✅ User naik ke level " + currentLevelName);

            JSONArray arr = new JSONArray();
            if (2 == arr.length()){

            }
        }
    }


    private void openGoogleMaps(double latitude, double longitude) {
        // Format URI untuk Google Maps
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude);

        // Intent ke Google Maps
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps"); // Buka langsung di Google Maps

        // Jalankan kalau aplikasi Google Maps tersedia
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps tidak terpasang", Toast.LENGTH_SHORT).show();
        }
    }

}

