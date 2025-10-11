package com.example.cafeku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafeku.database.AppDatabase;
import com.example.cafeku.model.CartItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView img = findViewById(R.id.detailImage);
        TextView name = findViewById(R.id.detailName);
        TextView price = findViewById(R.id.detailPrice);
        TextView description = findViewById(R.id.deskripsi);

        // Ambil data dari intent
        Intent intent = getIntent();
        String image = intent.getStringExtra("gambar");
        String nama = intent.getStringExtra("name");
        int harga = intent.getIntExtra("harga", 0);
        String deskripsi = intent.getStringExtra("deskripsi");

        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        price.setText(rupiah.format(harga));

        int resId = getResources().getIdentifier(image, "drawable", getPackageName());
        if (resId != 0) {
            img.setImageResource(resId);


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
                    CartItem newItem = new CartItem(nama, harga, image, 1);
                    db.cartDao().insert(newItem);
                }

                Intent intent1 = new Intent(DetailActivity.this,CartActivity.class);
                        startActivity(intent1);
                Toast.makeText(this, "Ditambahkan ke keranjang 🛒", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
