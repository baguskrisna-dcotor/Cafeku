package com.example.cafeku;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafeku.DAO.PointDao;
import com.example.cafeku.database.AppDatabase;
import com.example.cafeku.database.PointDatabase;
import com.example.cafeku.model.CartItem;
import com.example.cafeku.model.Point;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {


    private LinearLayout buybtn;

    private PointDatabase db;
    private TextView pointhandler,name,price,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView img = findViewById(R.id.detailImage);
         name = findViewById(R.id.detailName);
         price = findViewById(R.id.detailPrice);
         description = findViewById(R.id.deskripsi);
         buybtn = findViewById(R.id.buybtn);
         pointhandler = findViewById(R.id.txtCartpoint);


        // Ambil data dari intent
        Intent intent = getIntent();
        String image = intent.getStringExtra("gambar");
        String nama = intent.getStringExtra("name");
        int harga = intent.getIntExtra("harga", 0);
        String deskripsi = intent.getStringExtra("deskripsi");
        int point = intent.getIntExtra("point",0);

        pointhandler.setText(String.valueOf(point));

        db = PointDatabase.getInstance(this);
        PointDao p = db.pointDao();
        Point points = p.getPoints();

        buybtn.setOnClickListener(v ->{
            p.addpoint(points.id,point);
            Intent i = new Intent(DetailActivity.this, ThanksActivity.class);
            startActivity(i);
        });




        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        price.setText(rupiah.format(harga));
        try (InputStream is = getAssets().open(image)) {
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            img.setImageBitmap(bitmap);
            System.out.println(image);
            System.out.println(harga);
            name.setText(nama);

            description.setText(deskripsi);


            LinearLayout btnKeranjang = findViewById(R.id.keranjang);
            btnKeranjang.setOnClickListener(v -> {
                AppDatabase db = AppDatabase.getInstance(this);
                CartItem existing = db.cartDao().getItemByName(nama);

                if (existing != null) {
                    existing.quantity += 1;
                    db.cartDao().update(existing);
                } else {
                    CartItem newItem = new CartItem(nama,point, harga, image, 1);
                    db.cartDao().insert(newItem);
                }

                Intent intent1 = new Intent(DetailActivity.this,CartActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "Ditambahkan ke keranjang 🛒", Toast.LENGTH_SHORT).show();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
