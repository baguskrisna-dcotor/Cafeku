package com.example.cafeku;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView1 = findViewById(R.id.recyclerView);

        LinearLayoutManager linearlayoutmanager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        recyclerView1.setLayoutManager(linearlayoutmanager);

        List<MenuItem> menuList = new ArrayList<>();
        menuList.add(new MenuItem("Nasi Goreng", "Rp.20.000", "img_1.png"));
        menuList.add(new MenuItem("Mie Goreng", "Rp.18.000", "img_2.png"));
        menuList.add(new MenuItem("Ayam Bakar", "Rp.25.000", "img_3.png"));

        MenuAdapter adapter = new MenuAdapter(this, menuList);
        recyclerView1.setAdapter(adapter);
}}
