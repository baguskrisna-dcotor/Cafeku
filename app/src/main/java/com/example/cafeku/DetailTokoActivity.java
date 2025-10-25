package com.example.cafeku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cafeku.Supabase.SupabaseService;
import com.example.cafeku.database.UserDatabase;
import com.example.cafeku.model.User;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailTokoActivity extends BaseActivity{

    private RatingBar userrate, storerate;
    private Button send;
    private TextView tvrating;
    private LinearLayout seetrating, beforelogin;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_toko);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // === Inisialisasi UI ===
        userrate = findViewById(R.id.userrating);
        storerate = findViewById(R.id.tokorating);
        send = findViewById(R.id.sendrating);
        tvrating = findViewById(R.id.textrating);
        seetrating = findViewById(R.id.addrating);
        beforelogin = findViewById(R.id.beforelogin);

        // === Cek apakah activity ini dibuka melalui halaman loading ===
        boolean fromLoading = getIntent().getBooleanExtra("fromLoading", false);

        // === Load rating saat halaman dibuka ===
        loadAverageRating(() -> {
            if (fromLoading) {
                View rootView = findViewById(android.R.id.content);
                rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                        markReady(); // otomatis kirim broadcast
                        return true;
                    }
                });
            }

        });

        // === Cek login user ===
        User user = UserDatabase.getInstance(this).userDao().getUser();
        if (user != null) {
            checkAndSetRatingBarVisibility();
        } else {
            beforelogin.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Login dulu yuk sebelum kasih rating agar data kamu tersimpan!", Toast.LENGTH_SHORT).show();
            beforelogin.setOnClickListener(v -> {
                Intent i = new Intent(DetailTokoActivity.this, LoginActivity.class);
                startActivity(i);
            });
        }

        // Tombol ke halaman komentar
        LinearLayout review = findViewById(R.id.movetokomen);
        review.setOnClickListener(v -> startActivity(new Intent(DetailTokoActivity.this, ChatActivity.class)));

        // Tombol kembali
        String from = getIntent().getStringExtra("from");
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            if ("DetailActivity".equals(from)) {
                moveTo(MainActivity.class);
            } else if ("Profile".equals(from)) {
                moveTo(Profile.class);
            } else {
                finish();
            }
        });
    }

    /**
     * 🔹 Ambil rata-rata rating dari server dan tampilkan
     */
    private void loadAverageRating(Runnable complete) {
        SupabaseService supabaseService = new SupabaseService();
        supabaseService.getAverageRating(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body() != null ? response.body().string().trim() : "";
                response.close();
                Log.d("LOAD_AVG", "Response: " + result);

                if (response.isSuccessful() && !result.isEmpty()) {
                    try {
                        double avg = Double.parseDouble(result);
                        runOnUiThread(() -> {
                            float roundedAvg = (float) (Math.round(avg * 10.0) / 10.0);
                            storerate.setRating(roundedAvg);
                            DecimalFormat df = new DecimalFormat("#.#");
                            tvrating.setText(df.format(avg) + " /5");
                            if (complete != null) complete.run();
                        });
                    } catch (NumberFormatException e) {
                        Log.e("LOAD_AVG_ERROR", "Format rating tidak valid: " + result, e);
                        runOnUiThread(() ->
                                Toast.makeText(DetailTokoActivity.this, "Format data rating tidak valid.", Toast.LENGTH_LONG).show()
                        );
                        if (complete != null) complete.run();
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(DetailTokoActivity.this, "Gagal memuat rating rata-rata.", Toast.LENGTH_LONG).show()
                    );
                    if (complete != null) complete.run();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("LOAD_AVG_FAIL", "Gagal konek ke Supabase: " + e.getMessage());
                runOnUiThread(() -> {
                    Toast.makeText(DetailTokoActivity.this, "Tidak dapat terhubung ke server.", Toast.LENGTH_SHORT).show();
                    if (complete != null) complete.run();
                });
            }
        });
    }

    /**
     * 🔹 Cek apakah user sudah memberi rating
     * 🔹 Jika belum, tampilkan form rating
     */
    private void checkAndSetRatingBarVisibility() {
        SupabaseService supabaseService = new SupabaseService();
        User user = UserDatabase.getInstance(this).userDao().getUser();
        String username = user.username;
        int id = user.getId();

        supabaseService.checkUserRatingExists(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() -> seetrating.setVisibility(View.VISIBLE));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                response.close();
                final boolean ratingExists = response.isSuccessful() && !responseBody.equals("[]");

                mainHandler.post(() -> {
                    if (ratingExists) {
                        seetrating.setVisibility(View.GONE);
                    } else {
                        seetrating.setVisibility(View.VISIBLE);
                        send.setOnClickListener(v -> {
                            int ratingInt = Math.round(userrate.getRating());
                            if (ratingInt == 0) {
                                Toast.makeText(DetailTokoActivity.this, "Pilih rating dulu ya!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            supabaseService.insertRating(id, ratingInt, username, new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String result = response.body().string();
                                    response.close();
                                    Log.d("SUPABASE_INSERT", "Response: " + result);
                                    runOnUiThread(() -> {
                                        Toast.makeText(DetailTokoActivity.this, "Terima kasih atas ratingnya!", Toast.LENGTH_SHORT).show();
                                        seetrating.setVisibility(View.GONE);
                                        loadAverageRating(null); // ✅ Panggil ulang tanpa callback
                                    });
                                }

                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(() ->
                                            Toast.makeText(DetailTokoActivity.this, "Gagal kirim rating: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                    );
                                }
                            });
                        });
                    }
                });
            }
        });
    }

    /**
     * 🔹 Helper untuk pindah Activity dengan flag CLEAR_TOP
     */
    private void moveTo(Class<?> nameActivity) {
        Intent intent = new Intent(this, nameActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
