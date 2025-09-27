package com.example.cafeku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView img = findViewById(R.id.detailImage);
        TextView name = findViewById(R.id.detailName);
        TextView price = findViewById(R.id.detailPrice);

        // Ambil data dari intent
        Intent intent = getIntent();
        int image = intent.getIntExtra("img", 0);
        String nama = intent.getStringExtra("name");
        String harga = intent.getStringExtra("price");

        img.setImageResource(image);
        name.setText(nama);
        price.setText(harga);
    }
}
