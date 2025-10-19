package com.example.cafeku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VoucherDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voucher_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        int color = intent.getIntExtra("color",0);
        int image = intent.getIntExtra("img", 0);
        String nama = intent.getStringExtra("name");
        String deskripsi = intent.getStringExtra("deskripsi");
        LinearLayout adpColor = findViewById(R.id.adpColor);
        TextView txtName = findViewById(R.id.nameVoucher);
        TextView txtDeskrips = findViewById(R.id.deskripsi);
        ImageView imgVoucher = findViewById(R.id.imgVoucher);
        adpColor.setBackgroundResource(color);
        txtName.setText(nama);
        int resId = getResources().getIdentifier(String.valueOf(image), "drawable", getPackageName());
        imgVoucher.setImageResource(resId);
        int resColor = getResources().getIdentifier(String.valueOf(color), "drawable", getPackageName());
        adpColor.setBackgroundResource(resColor);
        txtDeskrips.setText(deskripsi);
    }
}