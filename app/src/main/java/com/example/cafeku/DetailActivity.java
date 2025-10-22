package com.example.cafeku;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
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
            if(points != null){
//                mengecek apakah user sudah punya point atau beluum , jika sudah point ditambah jika belum masukkan point baru
                p.addpoint(points.id,point);
            }else{
                Point pointbaru = new Point();
                pointbaru.totalPoint = point;
                p.insert(pointbaru);
            }
//          move ke halaman thanks animation
            Intent i = new Intent(DetailActivity.this, ThanksActivity.class);
            startActivity(i);
        });



// mengubah forman int biasa menjaid format rupiah dengan library di android studio
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        price.setText(rupiah.format(harga));

//        mengecek apakah gambar yang di intent ada atau tidak jika ada maka ambil gambar daari assest sesuai nama gambar yang di inetent
//
        if (image != null){
        try (InputStream is = getAssets().open(image)) {
            Bitmap bitmap = BitmapFactory.decodeStream(is);//Mengubah data mentah dari file gambar menjadi objek Bitmap
            // (format gambar yang bisa ditampilkan di Androi
            img.setImageBitmap(bitmap);//Menampilkan gambar hasil decode ke dalam ImageView dengan id img
//            opsinal(debugging)
            System.out.println(image);
            System.out.println(harga);
            name.setText(nama);

            description.setText(deskripsi);

//          jika user pencet keranjang maka semua atribut produk akan dimasukkan ke dalam data base
            LinearLayout btnKeranjang = findViewById(R.id.keranjang);
            btnKeranjang.setOnClickListener(v -> {
                AppDatabase db = AppDatabase.getInstance(this);
                CartItem existing = db.cartDao().getItemByName(nama);

//                mengecek apakah produk sudah ada dikeranjang jika ada maka ditambahkan ke
//                quantitynya jika belum ada tambah produk sebagai objek baru
                if (existing != null) {
                    existing.quantity += 1;
                    db.cartDao().update(existing);
                } else {
                    CartItem newItem = new CartItem(nama,point, harga, image, 1);
                    db.cartDao().insert(newItem);
                }
//                setelah pengecekan maka user akan alih halaman ke keranjang
                Intent intent1 = new Intent(DetailActivity.this,CartActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "Ditambahkan ke keranjang 🛒", Toast.LENGTH_SHORT).show();
            });
        } catch (IOException e) {
            e.printStackTrace();
        } }
//        jika data dari nama yang di intent tidak ada munculkan toast
        else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            Intent error = new Intent(this, errorPage.class);
            startActivity(error);
        }
    }

    public void switchDetToko(View view) {
        Intent intent = new Intent(this, DetailTokoActivity.class);
        intent.putExtra("from", "DetailActivity");
        startActivity(intent);

    }
}
