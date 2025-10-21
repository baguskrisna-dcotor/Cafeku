package com.example.cafeku;

import android.content.Context;
import android.view.ViewTreeObserver;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemChangeListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.cafeku.DAO.ImgDao;
import com.example.cafeku.DAO.LevelDao;
import com.example.cafeku.DAO.PointDao;
import com.example.cafeku.DAO.UserDao;
import com.example.cafeku.database.ImgDatabase;
import com.example.cafeku.database.LevelDatabase;
import com.example.cafeku.database.UserDatabase;
import com.example.cafeku.model.Img;
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
import org.w3c.dom.Text;

public class Profile extends AppCompatActivity implements OnMapReadyCallback {
    Handler handler = new Handler();
    private HorizontalScrollView h;
    private GoogleMap mMap;
    private ImageView btnMore, tvgender, photoprofile;
    private TextView tvusername, g1, g2;
    private ImageSlider slider;
    private LinearLayout l;
    ArrayList<String> namaList1 = new ArrayList<>();
    ArrayList<SlideModel> slideModels = new ArrayList<>();
    ArrayList<String> title1 = new ArrayList<>();
    ArrayList<String> title2 = new ArrayList<>();
    ArrayList<String> namalvl = new ArrayList<>();
    ArrayList<Integer> lvl = new ArrayList<>();
    ArrayList<Integer> minpoint = new ArrayList<>();

    ArrayList<String> titleimg = new ArrayList<>();
    ArrayList<String> imgchoice = new ArrayList<>();
    ArrayList<String> decs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        double latitude = -7.797068;
        double longitude = 110.370529;
        final int[] scroll = {0};
        final int[] speed = {3};

        tvusername = findViewById(R.id.nameUser);
        btnMore = findViewById(R.id.settingbutton);
        slider = findViewById(R.id.imageslider);
        g1 = findViewById(R.id.greetingtext1);
        g2 = findViewById(R.id.greetingtext2);
        tvgender = findViewById(R.id.gender);
        h = findViewById(R.id.scrollauto);
        l = findViewById(R.id.rankdetail);
        photoprofile = findViewById(R.id.photoprofile);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scroll[0] += 3; // geser 5px setiap loop
                h.smoothScrollTo(scroll[0], 0);


                if (scroll[0] >= h.getChildAt(0).getWidth()) {
                    scroll[0] = 0; // ulang ke awal
                }

                handler.postDelayed(this, speed[0]);
            }
        }, speed[0]);

        LevelHandler(namalvl, lvl, minpoint);

        try {
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
        boolean sex = i.getBooleanExtra("gender", false); // false = perempuan, true = laki-laki

        UserDao userDao = UserDatabase.getInstance(this).userDao();
        User currentUser = userDao.getUser(); // Ambil user yang sedang login

        if (currentUser != null) {
            tvusername.setText(currentUser.username);
            ImgDatabase id = ImgDatabase.getInstance(this);
            ImgDao dao = id.imgDao();
            Img existingImg = dao.select();


            if (currentUser.gender) {
                tvgender.setImageResource(R.drawable.male);
            } else {
                tvgender.setImageResource(R.drawable.remale);
            }

            if (existingImg != null && existingImg.img != null) {
                try {
                    InputStream is2 = getAssets().open("imageprofile/" + existingImg.img + ".png");
                    Bitmap bmp = BitmapFactory.decodeStream(is2);
                    photoprofile.setImageBitmap(bmp);
                    is2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IMG_LOAD", "Gagal memuat gambar: " + existingImg.img, e);
                    photoprofile.setImageResource(R.drawable.icon_guest);
                }
            } else {
                photoprofile.setImageResource(R.drawable.icon_guest);
                Toast.makeText(this, "Kamu belum set gambar profil.", Toast.LENGTH_SHORT).show();
            }

        } else {
            photoprofile.setImageResource(R.drawable.cafeku);
            tvusername.setText("Halo, Guest");
            photoprofile.setImageResource(R.drawable.icon_guest);
            tvgender.setImageResource(R.drawable.icon_guest); // opsional
        }


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
                R.id.rankdetail
        };
        Class[] moveto = {
                MainActivity.class,
                VoucherActivity.class,
                CartActivity.class,
                VoucherActivity.class,
                RankActivity.class};

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
        LatLng cafeLocation = new LatLng(-7.15, 111.88);
        mMap.addMarker(new MarkerOptions().position(cafeLocation).title("Cafeku"));
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
        tvgender = findViewById(R.id.gender);
        tvusername = findViewById(R.id.nameUser);

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            UserDatabase db = UserDatabase.getInstance(this);
            UserDao userDao = db.userDao();
            User user = userDao.getUser();

            ImgDatabase ib = ImgDatabase.getInstance(this);
            ImgDao dao = ib.imgDao();

            LevelDao level = LevelDatabase.getInstance(this).levelDao();


            if (id == R.id.menu_edit_profile) {
                showEditProfileDialog();
                return true;

            } else if (id == R.id.menu_login) {
                if (user == null) {
                    Toast.makeText(this, "Silakan login dulu", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Profile.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Kamu sudah login sebagai " + user.username, Toast.LENGTH_SHORT).show();
                }
                return true;

            } else if (id == R.id.menu_logout) {
                if (user != null) {
                    userDao.logout();
                    dao.delete();
                    level.delete();
                    photoprofile.setImageResource(R.drawable.icon_guest);
                    tvusername.setText("Hallo,Guest");
                    tvgender.setImageResource(R.drawable.icon_guest);
                    Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Kamu belum login", Toast.LENGTH_SHORT).show();
                }
                return true;
            } else if (id == R.id.Edit_Img) {
                showImageChoice();
                Toast.makeText(this, "Edit IMG", Toast.LENGTH_SHORT).show();
                Log.d("MENU", "Edit profile clicked");
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
            UserDatabase u = UserDatabase.getInstance(this);
            u.userDao().updateNama(name);
            tvusername.setText(name);

            Toast.makeText(this, "Data tersimpan!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        // klik tombol X (close)
        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showImageChoice() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.image_choice, null);

        TextView[] name = {
                view.findViewById(R.id.name1),
                view.findViewById(R.id.name2),
                view.findViewById(R.id.name3),
                view.findViewById(R.id.name4),
                view.findViewById(R.id.name5),

        };
        TextView[] title = {
                view.findViewById(R.id.title1),
                view.findViewById(R.id.title2),
                view.findViewById(R.id.title3),
                view.findViewById(R.id.title4),
                view.findViewById(R.id.title5)};
        TextView[] deskripsi = {
                view.findViewById(R.id.decs1),
                view.findViewById(R.id.decs2),
                view.findViewById(R.id.decs3),
                view.findViewById(R.id.decs4),
                view.findViewById(R.id.decs5)};
        ImageView[] imageViews = {
                view.findViewById(R.id.img_1),
                view.findViewById(R.id.img_2),
                view.findViewById(R.id.img_3),
                view.findViewById(R.id.img_4),
                view.findViewById(R.id.img_5)
        };

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create();


        try {
            InputStream is = getAssets().open("profileimg.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                imgchoice.add(obj.getString("nama"));
                titleimg.add(obj.getString("Title"));
                decs.add(obj.getString("desc"));
            }

            for (int i =0; i <= imageViews.length  && i < imgchoice.size(); i++) {
                final int index = i;
                InputStream imgStream = getAssets().open("imageprofile/" + imgchoice.get(i) + ".png");
                Bitmap bitmap = BitmapFactory.decodeStream(imgStream);
                imageViews[i].setImageBitmap(bitmap);
                imgStream.close();

                title[index].setText(titleimg.get(index));
                deskripsi[index].setText(decs.get(index));
                name[index].setText(titleimg.get(index));

                imageViews[i].setOnClickListener(v -> {
                    ImgDatabase id = ImgDatabase.getInstance(this);
                    ImgDao dao = id.imgDao();
                    Img existing = dao.select();


                    if (existing != null) {
                        // update record yang ada

                        dao.update(existing.id,imgchoice.get(index));
                    } else {
                        // insert record baru
                        Img newImg = new Img();
                        newImg.img =imgchoice.get(index);
                        dao.insert(newImg);
                    }

                    // langsung set photoprofile dari asset yang kita pilih
                    try {
                        InputStream is2 = getAssets().open("imageprofile/" + imgchoice.get(index) + ".png");
                        Bitmap bmp = BitmapFactory.decodeStream(is2);
                        ImageView photoprofile = findViewById(R.id.photoprofile);
                        Toast.makeText(this,"Photo profile anda diperbarui",Toast.LENGTH_SHORT).show();
                        photoprofile.setImageBitmap(bmp);
                        is2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("IMG_SAVE", "Gagal load asset after save: " +imgchoice.get(index), e);
                    }


                    Toast.makeText(this, "Gambar disimpan: " + imgchoice.get(index), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal memuat data gambar", Toast.LENGTH_SHORT).show();
        }

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

        try {
            InputStream is = getAssets().open("Level.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);


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
            return;
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

                lvldao.insert(new LevelModel(currentLevel, currentLevelName, requiredPoint));


            TextView tvLevelname = findViewById(R.id.levelname);
            ProgressBar progressbar = findViewById(R.id.progressBar);


            int nextMinPoint;
            if (achievedIndex + 1 < minPoint.size()) {
                nextMinPoint = minPoint.get(achievedIndex + 1);
            } else {
                nextMinPoint = minPoint.get(achievedIndex);
            }
            int progress;
            if (nextMinPoint == requiredPoint) {
                progress = 100;
            } else {
                progress = (int) (((float) (userPoint - requiredPoint) / (nextMinPoint - requiredPoint)) * 100);
            }
            progressbar.setProgress(progress);

            ObjectAnimator animation = ObjectAnimator.ofInt(progressbar, "progress", progressbar.getProgress(), progress);
            animation.setDuration(800);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();


            switch (currentLevelName) {
                case "Keren":
                    tvLevelname.setTextColor(Color.parseColor("#B0BEC5"));

                    break;
                case "Ksatria":
                    tvLevelname.setTextColor(Color.parseColor("#ECEFF1"));

                    break;
                case "Pangeran":
                    tvLevelname.setTextColor(Color.parseColor("#FFD54F"));

                    break;
                case "Raja":
                    tvLevelname.setTextColor(Color.parseColor("#D1C4E9"));

                    break;
                case "Mitos":
                    tvLevelname.post(() -> {
                        TextView tv = tvLevelname;
                        tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

                        int width = tv.getWidth();
                        if (width <= 0) return;

                        // Warna gradasi Divine Gold
                        int[] colors = {
                                Color.parseColor("#3B0000"),
                                Color.parseColor("#7B1113"),
                                Color.parseColor("#C21807"),
                                Color.parseColor("#FF1744"),
                                Color.parseColor("#FFD5D5")
                        };

                        // Gradasi horizontal (kiri ke kanan)
                        LinearGradient gradient = new LinearGradient(
                                0, 0, width, 0,
                                colors, null, Shader.TileMode.CLAMP);

                        Paint paint = tv.getPaint();
                        paint.setShader(gradient);

                        tv.invalidate();
                    });
                    break;
            }

            tvLevelname.setText(currentLevelName);
            Log.d("LevelHandler", "✅ User naik ke level " + currentLevelName);

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

