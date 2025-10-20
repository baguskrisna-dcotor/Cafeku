package com.example.cafeku;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.media.ImageWriter;
import android.os.Bundle;
import android.view.View;
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

        int[] images = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4};
        String[] names = {"Eid Mubarak", "Valentines Day", "Chinese New Year", "Winter"};
        String[] detail = {"Diskon GILA!!!, Semua produk potongan 30%! Buruan sebelum 28 Februari! ","Cinta makin hemat! Semua produk couple diskon 14% cuma sampai 14 Februari!","Gong Xi Fa Cai! Belanja apa aja potongan 15% selama Imlek!!","Harga turun 20%! Nikmati promo dingin-dingin hemat sebelum Februari Terakhir!"};
        int[] color = {R.drawable.vcr_green,R.drawable.vcr_pink,R.drawable.vcr_red,R.drawable.vcr_blue};

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voucher);
        tambahProduk(imgIds, imgIds, txtIds, images, names, detail, color);
    }
public void tambahProduk(int[] clickable, int[] imgId, int[] txtId, int[] image, String[] name, String[] det, int[] color) {
    for (int i = 0; i < name.length; i++) {
        int index = i;
        ImageView img = findViewById(imgId[i]);
        TextView txtTittle = findViewById(txtId[i]);
        ImageView clickAble = findViewById(clickable[i]);

        img.setImageResource(image[i]);
        txtTittle.setText(name[i]);


        clickAble.setOnClickListener(v -> {
            Intent intent = new Intent(this, VoucherDetailActivity.class);
            intent.putExtra("img", image[index]);
            intent.putExtra("name", name[index]);
            intent.putExtra("deskripsi", det[index]);
            intent.putExtra("color", color[index]);
            startActivity(intent);
        });
    }
}

    public void switchHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    public void switchVoucher(View view) {
        Intent intent = new Intent(this, VoucherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    public void switchKeranjang(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    public void switchProfile(View view) {
        Intent intent = new Intent(this, Profile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
