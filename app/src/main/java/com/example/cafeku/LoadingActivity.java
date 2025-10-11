package com.example.cafeku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Simulasikan loading 3 detik lalu ke Thanks screen
        new Handler().postDelayed(() -> {
            startActivity(new Intent(LoadingActivity.this, CartActivity.class));
            finish();
        }, 3000);
    }
}
