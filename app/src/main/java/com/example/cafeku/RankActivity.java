package com.example.cafeku; // ganti sesuai package project kamu

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import com.example.cafeku.model.LevelModel;
import com.example.cafeku.model.Point;
import com.example.cafeku.database.PointDatabase;
import com.example.cafeku.DAO.LevelDao;
import com.example.cafeku.DAO.PointDao;
import com.example.cafeku.database.LevelDatabase;

public class RankActivity extends AppCompatActivity {
    private TextView point, resetpoint,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);
        point = findViewById(R.id.point);
        back = findViewById(R.id.back);

        back.setOnClickListener(v->{
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
        LevelDatabase lvldb = LevelDatabase.getInstance(this);
        LevelDao lvldao = lvldb.levelDao();

        Point p = PointDatabase.getInstance(this).pointDao().getPoints();
        int userPoint = (p != null ? p.totalPoint : 0);

        JSONArray jsonArray;

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

            LevelModel exist = lvldao.getLevelById(currentLevel);
            if (exist == null) {
                lvldao.insert(new LevelModel(currentLevel, currentLevelName, requiredPoint));
            }

            TextView tvLevelname = findViewById(R.id.levelname);
            TextView tvMinPoint = findViewById(R.id.minpoint);
            TextView tvlevel = findViewById(R.id.lvlnow);
            ImageView img = findViewById(R.id.imagelevel);
            ProgressBar progressbar = findViewById(R.id.progressBar);

            Animation updown = AnimationUtils.loadAnimation(this, R.anim.updown);
            img.startAnimation(updown);

            int nextMinPoint = (achievedIndex + 1 < minPoint.size()) ? minPoint.get(achievedIndex + 1) : minPoint.get(achievedIndex);
            int progress = (int) (((float) (userPoint - requiredPoint) / (nextMinPoint - requiredPoint)) * 100);
            progressbar.setProgress(progress);

            ObjectAnimator animation = ObjectAnimator.ofInt(progressbar, "progress", progressbar.getProgress(), progress);
            animation.setDuration(800);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();



            String imageName = "image_level" + (achievedIndex + 1);
            int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            img.setImageResource(resId != 0 ? resId : R.drawable.dummy);

            if (Objects.equals(currentLevelName, "Keren")) {
                tvLevelname.setTextColor(Color.parseColor("#B0BEC5"));

            } else if (Objects.equals(currentLevelName, "Ksatria")) {
                tvLevelname.setTextColor(Color.parseColor("#ECEFF1"));

            } else if (Objects.equals(currentLevelName, "Pangeran")) {
                tvLevelname.setTextColor(Color.parseColor("#FFD54F"));

            } else if (Objects.equals(currentLevelName, "Raja")) {
                tvLevelname.setTextColor(Color.parseColor("#D1C4E9"));

            } else if (Objects.equals(currentLevelName, "Mitos")) {
                TextView tv = tvLevelname;
                tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

                tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int width = tv.getWidth();
                        if (width <= 0) return;

                        int[] colors = {
                                Color.parseColor("#8B0000"),
                                Color.parseColor("#B22222"),
                                Color.parseColor("#D32F2F"),
                                Color.parseColor("#E53935"),
                                Color.parseColor("#F44336"),
                                Color.parseColor("#FF7043"),
                                Color.parseColor("#FFD180")
                        };

                        LinearGradient gradient = new LinearGradient(0, 0, width * 2, 0, colors, null, Shader.TileMode.MIRROR);
                        Paint paint = tv.getPaint();
                        paint.setShader(gradient);

                        Matrix matrix = new Matrix();
                        ValueAnimator animator = ValueAnimator.ofFloat(0, width * 2);
                        animator.setDuration(4000);
                        animator.setRepeatCount(ValueAnimator.INFINITE);
                        animator.setInterpolator(new LinearInterpolator());

                        animator.addUpdateListener(anim -> {
                            float translateX = (float) anim.getAnimatedValue();
                            matrix.setTranslate(translateX, 0);
                            gradient.setLocalMatrix(matrix);
                            paint.setShader(gradient);
                            tv.postInvalidateOnAnimation();
                        });
                        animator.start();
                        tv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }

            // ====== Text display ======
            tvLevelname.setText(currentLevelName);
            tvlevel.setText("Level " + currentLevel);
            tvMinPoint.setText("of " + nextMinPoint);

            if (currentLevel == 1) {
                tvlevel.setTextColor(Color.GRAY);
            } else if (currentLevel == 2) {
                tvlevel.setTextColor(Color.BLUE);
            } else if (currentLevel == 3) {
                tvlevel.setTextColor(Color.YELLOW);
            } else if (currentLevel == 4) {
                tvlevel.setTextColor(Color.parseColor("#673AB7FF"));
            } else {
                tvlevel.setTextColor(Color.RED);
            }

            Log.d("LevelHandler", "✅ User naik ke level " + currentLevelName);
            resetpoint = findViewById(R.id.resetpoint);
            resetpoint.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi Reset")
                        .setMessage("Apakah kamu yakin ingin mereset semua point?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            PointDatabase dp = PointDatabase.getInstance(this);
                            dp.pointDao().deleteAll();

                            // Reset tampilan awal
                            progressbar.setProgress(0);
                            tvlevel.setText(String.valueOf(level.get(0)));
                            tvLevelname.setText(namaLevel.get(0));
                            tvMinPoint.setText(String.valueOf(minPoint.get(0)));
                            img.setImageResource(R.drawable.image_level1);
                            point.setText("0");

                            // Hapus level tersimpan jika ada
                            LevelDatabase lvl = LevelDatabase.getInstance(this);
                            LevelDao dao = lvl.levelDao();
                            dao.delete();

                            // Jalankan ulang LevelHandler agar tampilan level sinkron
                            LevelHandler(namaLevel, level, minPoint);

                            Toast.makeText(this, "Point berhasil direset", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss())
                        .show();
            });

        }
    }
}

