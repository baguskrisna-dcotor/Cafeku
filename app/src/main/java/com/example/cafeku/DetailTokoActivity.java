package com.example.cafeku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailTokoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_toko);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        LinearLayout review = findViewById(R.id.movetokomen);
        review.setOnClickListener(v->{
            startActivity(new Intent(DetailTokoActivity.this, ChatActivity.class));
        });
        String from = getIntent().getStringExtra("from");

        ImageView back = findViewById(R.id.back);

        //Mengecek dari mana user masuk.
        //Apakah dari detailActivity atau profile
        back.setOnClickListener(v -> {
            switch (from){
                case "DetailActivity":
                    getIntent(MainActivity.class);
                    break;
                case "Profile":
                    getIntent(Profile.class);
                    break;
            }
        });
    }

    //Method untuk berpindah Activity sesuai parameter yang dimasukkan hasil switch case di atas.
    //Menggunakan Intent
    private void getIntent(Class<?> nameActivity){
        Intent intent = new Intent(this, nameActivity);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}