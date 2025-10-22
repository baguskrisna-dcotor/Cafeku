package com.example.cafeku;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cafeku.DAO.LevelDao;
import com.example.cafeku.database.LevelDatabase;
import com.example.cafeku.database.PointDatabase;
import com.example.cafeku.model.LevelModel;
import com.example.cafeku.model.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class RankActivity extends AppCompatActivity {
    private TextView point, resetpoint, back;
    private MediaPlayer soundeffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);
        point = findViewById(R.id.point);
        back = findViewById(R.id.back);

        back.setOnClickListener(v -> {
            Intent i = new Intent(RankActivity.this, Profile.class);
            startActivity(i);
        });

        ArrayList<String> namaLevel = new ArrayList<>();
        ArrayList<Integer> level = new ArrayList<>();
        ArrayList<Integer> minPoint = new ArrayList<>();


        LevelHandler(namaLevel, level, minPoint);
        Point p = PointDatabase.getInstance(this).pointDao().getPoints();
        point.setText(String.valueOf(p != null ? p.totalPoint : 0));
    }

    private void LevelHandler(
            ArrayList<String> namaLevel,
            ArrayList<Integer> level,
            ArrayList<Integer> minPoint
    ) {

        //Mengambil instance database LevelDatabase dan PointDatabase.
        LevelDatabase lvldb = LevelDatabase.getInstance(this);
        LevelDao lvldao = lvldb.levelDao();
        //Mengambil total poin user (userPoint) dari database.
        Point p = PointDatabase.getInstance(this).pointDao().getPoints();
        int userPoint = (p != null ? p.totalPoint : 0);      //Jika p kosong (belum ada data), maka nilainya 0.

        JSONArray jsonArray;

        try {
            InputStream is = getAssets().open("Level.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
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
            return;
        }
        //Loop ini mencari level tertinggi yang bisa dicapai berdasarkan poin user.
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
            //Menyimpan level ke database jika belum ada — agar bisa digunakan ulang nanti.
            LevelModel exist = lvldao.getLevelById(currentLevel);
            if (exist == null) {
                //Menyimpan level ke database jika belum ada — agar bisa digunakan ulang nanti.
                lvldao.insert(new LevelModel(currentLevel, currentLevelName, requiredPoint));
            }
            //Semua komponen tampilan diambil dari layout untuk menampilkan level, nama, gambar, progress, dll.
            TextView tvLevelname = findViewById(R.id.levelname);
            TextView tvMinPoint = findViewById(R.id.minpoint);
            TextView tvlevel = findViewById(R.id.lvlnow);
            ImageView img = findViewById(R.id.imagelevel);
            ProgressBar progressbar = findViewById(R.id.progressBar);

            Animation updown = AnimationUtils.loadAnimation(this, R.anim.updown);
            img.startAnimation(updown);

            //Menghitung berapa persen progress ke level berikutnya.
            int nextMinPoint = (achievedIndex + 1 < minPoint.size()) ? minPoint.get(achievedIndex + 1) : minPoint.get(achievedIndex);
            int progress = (int) (((float) (userPoint - requiredPoint) / (nextMinPoint - requiredPoint)) * 100);
            progressbar.setProgress(progress);


            //progressbar → objek target (ProgressBar di layout).
            //"progress" → nama properti yang akan dianimasikan (yakni level progress-nya).
            //progressbar.getProgress() → nilai awal animasi (misalnya 40%).
            //progress → nilai akhir animasi (misalnya 80%).
            ObjectAnimator animation = ObjectAnimator.ofInt(progressbar, "progress", progressbar.getProgress(), progress);
            animation.setDuration(3000);
            animation.setInterpolator(new DecelerateInterpolator());//animasi akan mulai dengan cepat lalu melambat di akhir.
            animation.start();//mulai animasi

            //Otomatis ambil gambar dari drawable/image_level1, drawable/image_level2, dst.
            String imageName = "image_level" + (achievedIndex + 1);
            int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            img.setImageResource(resId != 0 ? resId : R.drawable.dummy);
            //set warna dan suara untuk setiap level
            if (Objects.equals(currentLevelName, "Keren")) {
                soundeffect = MediaPlayer.create(this, R.raw.lv2);
                soundeffect.start();
                tvLevelname.setTextColor(Color.parseColor("#B0BEC5"));

            } else if (Objects.equals(currentLevelName, "Ksatria")) {
                soundeffect = MediaPlayer.create(this, R.raw.lv1);
                soundeffect.start();
                tvLevelname.setTextColor(Color.parseColor("#ECEFF1"));

            } else if (Objects.equals(currentLevelName, "Pangeran")) {
                soundeffect = MediaPlayer.create(this, R.raw.lv3);
                soundeffect.start();
                tvLevelname.setTextColor(Color.parseColor("#FFD54F"));

            } else if (Objects.equals(currentLevelName, "Raja")) {
                soundeffect = MediaPlayer.create(this, R.raw.lv4);
                soundeffect.start();
                tvLevelname.setTextColor(Color.parseColor("#D1C4E9"));

            } else if (Objects.equals(currentLevelName, "Mitos")) {
                //Mitos ada lah max level jadi beri beberapa perubahan pada layout
                LinearLayout l = findViewById(R.id.rankbg);
                LinearLayout l2 = findViewById(R.id.rankbg2);
                l.setBackgroundResource(R.drawable.mitosplaceholder);
                l2.setBackgroundResource(R.drawable.mitosplaceholder);
                soundeffect = MediaPlayer.create(this, R.raw.lv5);
                soundeffect.start();
                tvLevelname.setTextColor(Color.parseColor("#3B0000"));
            }
            // ====== Text display ======
            tvLevelname.setText(currentLevelName);
            tvMinPoint.setText("of " + nextMinPoint);
//             set warna text sesuai level
            if (currentLevel == 1) {
                tvlevel.setTextColor(Color.GRAY);
            } else if (currentLevel == 2) {
                tvlevel.setTextColor(Color.BLUE);

            } else if (currentLevel == 3) {
                tvlevel.setTextColor(Color.YELLOW);
            } else if (currentLevel == 4) {
                tvlevel.setTextColor(Color.parseColor("#673AB7FF"));
            } else {
                tvlevel.setBackgroundResource(R.drawable.mitosplaceholder);
                tvlevel.setTextColor(Color.RED);
            }
            tvlevel.setText("Level " + currentLevel);

            Log.d("LevelHandler", "✅ User naik ke level " + currentLevelName);
            resetpoint = findViewById(R.id.resetpoint);
            resetpoint.setOnClickListener(v -> {

            //Membuat kotak dialog konfirmasi (pop-up) dengan AlertDialog
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi Reset")//judul
                        .setMessage("Apakah kamu yakin ingin mereset semua point?")//pesan utama
                        //button setuju
                        .setPositiveButton("Ya", (dialog, which) -> {
                            //koneksi kedata base
                            PointDatabase dp = PointDatabase.getInstance(this);
                            dp.pointDao().deleteAll();
                            recreate();
                            // Hapus level tersimpan jika ada
                            LevelDatabase lvl = LevelDatabase.getInstance(this);
                            LevelDao dao = lvl.levelDao();
                            dao.delete();

                            // Jalankan ulang LevelHandler agar tampilan level sinkron
                            LevelHandler(namaLevel, level, minPoint);

                            Toast.makeText(this, "Point berhasil direset", Toast.LENGTH_SHORT).show();
                        })
                        //button batal reset
                        //jika dipencet langsung dismiss
                        .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss())
                        .show();
            });

        }
    }
}

