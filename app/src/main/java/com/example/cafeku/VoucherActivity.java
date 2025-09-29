package com.example.cafeku;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class VoucherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // data data
        int[] imgIds = {R.id.imgVoucher1, R.id.imgVoucher2, R.id.imgVoucher3, R.id.imgVoucher4};
        int[] txtIds = {R.id.titleVoucher1, R.id.titleVoucher2, R.id.titleVoucher3, R.id.titleVoucher4};
        int[] detIds = {R.id.detTxt1, R.id.detTxt2, R.id.detTxt3, R.id.detTxt4};
        int[] detVoucherIds = {R.id.detVoucher1, R.id.detVoucher2, R.id.detVoucher3, R.id.detVoucher4};

        int[] images = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4};
        String[] names = {"Eid Mubarak", "Valentines Day", "Chinese New Year", "Winter"};
        String[] detail = {"Abis puasa 30 hari langsung dapat diskon 30%!!","Yuk ngopi bareng bubub tercintah pakai diskon ini","Angpao buat ngopi bisa nih mumpung ada diskon","Dingin-dingin enaknya ngopi bareng temen/soulmate kamu nich"};

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voucher);
        tambahProduk(detVoucherIds, imgIds, txtIds, detIds, images, names, detail);
    }
public void tambahProduk(int[] clickable, int[] imgId, int[] txtId, int[] detId, int[] image, String[] name, String[] det) {
    for (int i = 0; i < name.length; i++) {
        int index = i;

        ImageView img = findViewById(imgId[i]);
        TextView txtTittle = findViewById(txtId[i]);
        TextView txtDetail = findViewById(detId[i]);
        LinearLayout bttDetail = findViewById(clickable[i]);

        img.setImageResource(image[i]);
        txtTittle.setText(name[i]);
        txtDetail.setText(det[i]);

        bttDetail.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("img", image[index]);
            intent.putExtra("name", name[index]);
            intent.putExtra("price", det[index]);
            startActivity(intent);
        });
    }
}}
