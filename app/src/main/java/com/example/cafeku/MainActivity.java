package com.example.cafeku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int[] parent1 = {R.id.katalog1, R.id.katalog2, R.id.katalog3, R.id.katalog4, R.id.katalog5};
        int[] imgIds1 = {R.id.imgMenu1, R.id.imgMenu2, R.id.imgMenu3, R.id.imgMenu4, R.id.imgMenu5};
        int[] txtIds1 = {R.id.txtMenu1, R.id.txtMenu2, R.id.txtMenu3, R.id.txtMenu4, R.id.txtMenu5};
        int[] btnIds1 = {R.id.btnMenu1, R.id.btnMenu2, R.id.btnMenu3, R.id.btnMenu4, R.id.btnMenu5};

        int[] images1 = {R.drawable.menu_1, R.drawable.menu_2, R.drawable.menu_3, R.drawable.menu_5, R.drawable.menu_6};
        String[] names1 = {"Kentang Goreng", "Burger", "Sosis Bakar", "Pizza", "Nasi Goreng"};
        String[] prices1 = {"15000", "Rp 20.000", "Rp 10.000", "Rp 30.000", "Rp 25.000"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tambahProduk(parent1, imgIds1, txtIds1, btnIds1, images1, names1, prices1);

        ImageSlider imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();

        imageList.add(new SlideModel(R.drawable.homeimg, "homeimg1", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_2, "homeimg2", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_3, "homeimg3", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_4, "homeimg4", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(imageList);




}

    public void tambahProduk(int[] katalogId, int[] imgId, int[] txtId, int[] btnId, int[] image, String[] name, String[] price){
        for (int i = 0; i < name.length; i++) {
            int index = i;

            ImageView img = findViewById(imgId[i]);
            TextView txt = findViewById(txtId[i]);
            MaterialButton btn = findViewById(btnId[i]);
            LinearLayout katalog = findViewById(katalogId[i]);

            img.setImageResource(image[i]);
            txt.setText(name[i]);
            btn.setText(price[i]);

            katalog.setOnClickListener(v -> {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("img", image[index]);
                intent.putExtra("name", name[index]);
                intent.putExtra("price", price[index]);
                startActivity(intent);
            });
        }
    }}
