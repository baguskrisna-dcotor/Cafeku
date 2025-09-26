package com.example.cafeku;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.google.android.material.slider.Slider;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageSlider imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> imageList = new ArrayList<>();

        imageList.add(new SlideModel(R.drawable.homeimg, "homeimg1", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_2, "homeimg2", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_3, "homeimg3", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.homeimg_4, "homeimg4", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(imageList);


}}
