package com.example.cafeku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        Integer image = intent.getIntExtra("img",0);
        String nama = intent.getStringExtra("name");
        String deskripsi = intent.getStringExtra("deskripsi");
        TextView txtName = findViewById(R.id.nameVoucher);
        TextView txtDeskrips = findViewById(R.id.deskripsi);
        ImageView imgVoucher = findViewById(R.id.imgVoucher);

        if (image == null) {
            Toast.makeText(this,"kosong bego",Toast.LENGTH_SHORT).show();
        }else{
            int resId = getResources().getIdentifier(String.valueOf(image), "drawable", getPackageName());
            imgVoucher.setImageResource(resId);
        }


        txtName.setText(nama);
        txtDeskrips.setText(deskripsi);

    }
}