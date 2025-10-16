package com.example.cafeku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeku.DAO.PointDao;
import com.example.cafeku.R;
import com.example.cafeku.adapter.CartAdapter;
import com.example.cafeku.database.AppDatabase;
import com.example.cafeku.database.PointDatabase;
import com.example.cafeku.model.CartItem;
import com.example.cafeku.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout addproduk;
    private TextView txtTotal, txtDate, pointholder;
    private CartAdapter adapter;
    private AppDatabase db;
    private PointDatabase dbs;
    private Button beli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keranjang);

        beli = findViewById(R.id.btnCheckout);
        addproduk = findViewById(R.id.addproduct);
        pointholder = findViewById(R.id.txtpoint);
        recyclerView = findViewById(R.id.recyclerViewCart);
        txtTotal = findViewById(R.id.txtTotal);
        txtDate = findViewById(R.id.datetime);

        String currentDate = new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date());
        txtDate.setText(currentDate);

        addproduk.setOnClickListener(v -> {
            Intent i = new Intent(CartActivity.this, MainActivity.class);
            startActivity(i);
        });

        db = AppDatabase.getInstance(this);


        List<CartItem> cartItems = db.cartDao().getAllItems();


        adapter = new CartAdapter(this, cartItems, (total, totalpoint) -> {
            txtTotal.setText("Total: Rp " + total);
            pointholder.setText("+" + totalpoint + " Point");

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateTotal(cartItems);
        updateTotalpoint(cartItems);

        beli.setOnClickListener(v -> {
            adapter.clear();
            Toast.makeText(this, "Keranjang anda sekarang kosong. Terimakasih sudah membeli", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ThanksActivity.class);
            startActivity(intent);
        });
    }

    private void updateTotal(List<CartItem> items) {
        double total = 0;
        for (CartItem i : items) {
            total += i.price * i.quantity;
        }
        txtTotal.setText("Total: Rp " + total);
    }

    private void updateTotalpoint(List<CartItem> items) {
        int total = 0;
        dbs = PointDatabase.getInstance(this);
        PointDao p = dbs.pointDao();
        Point point = p.getPoints();
        for (CartItem i : items) {
            total += i.point * i.quantity;
        }
        if (point != null) {
            int newTotal = point.totalPoint + total;
            p.addpoint(point.id, newTotal);
        } else {
            Point newPoint = new Point();
            newPoint.totalPoint = total;
            p.insert(newPoint);
        }
        pointholder.setText(String.valueOf(total));
    }
}

