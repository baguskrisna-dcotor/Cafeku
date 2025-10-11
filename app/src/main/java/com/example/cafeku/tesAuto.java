package com.example.cafeku;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.io.InputStream;


public class tesAuto extends AppCompatActivity {

    int[] parent1 = {R.id.katalog1, R.id.katalog2, R.id.katalog3, R.id.katalog4, R.id.katalog5};
    int[] imgIds1 = {R.id.imgMenu1, R.id.imgMenu2, R.id.imgMenu3, R.id.imgMenu4, R.id.imgMenu5};
    int[] txtIds1 = {R.id.txtMenu1, R.id.txtMenu2, R.id.txtMenu3, R.id.txtMenu4, R.id.txtMenu5};
    int[] btnIds1 = {R.id.btnMenu1, R.id.btnMenu2, R.id.btnMenu3, R.id.btnMenu4, R.id.btnMenu5};

    String[] imagePaths = {
            "images/menu_1.png",
            "images/menu_2.png",
            "images/menu_3.png",
            "images/menu_4.png",
            "images/menu_5.png"
    };
    String[] names1 = {"Kentang Goreng", "Burger", "Sosis Bakar", "Pizza", "Nasi Goreng"};
    String[] prices1 = {"15000", "Rp 20.000", "Rp 10.000", "Rp 30.000", "Rp 25.000"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tes_auto);
        tambahProduk(parent1, imgIds1, txtIds1, btnIds1, imagePaths, names1, prices1);
    }
    public void tambahProduk(int[] katalogId, int[] imgId, int[] txtId, int[] btnId, String[] imagePath, String[] name, String[] price){
        for (int i = 0; i < name.length; i++) {
            int index = i;

            ImageView img = findViewById(imgId[i]);
            TextView txt = findViewById(txtId[i]);
            MaterialButton btn = findViewById(btnId[i]);
            LinearLayout katalog = findViewById(katalogId[i]);

            txt.setText(name[i]);
            btn.setText(price[i]);
            try (InputStream is = getAssets().open(imagePath[i])) {
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


            katalog.setOnClickListener(v -> {
                Intent intent = new Intent(tesAuto.this, DetailActivity.class);
                intent.putExtra("img", imgId[index]);
                intent.putExtra("name", name[index]);
                intent.putExtra("price", price[index]);
                startActivity(intent);
            });
        }
    }
}
