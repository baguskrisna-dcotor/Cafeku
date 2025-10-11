package com.example.cafeku;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cafeku.R;
import com.example.cafeku.adapter.CartAdapter;
import com.example.cafeku.database.AppDatabase;
import com.example.cafeku.model.CartItem;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtTotal;
    private CartAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keranjang);

        recyclerView = findViewById(R.id.recyclerViewCart);
        txtTotal = findViewById(R.id.txtTotal);

        db = AppDatabase.getInstance(this);

        // Ambil semua item dari database
        List<CartItem> cartItems = db.cartDao().getAllItems();

        adapter = new CartAdapter(this, cartItems, total -> {
            // callback dari adapter untuk update total harga
            txtTotal.setText("Total: Rp " + total);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // tampilkan total awal
        updateTotal(cartItems);
    }

    private void updateTotal(List<CartItem> items) {
        double total = 0;
        for (CartItem i : items) {
            total += i.price * i.quantity;
        }
        txtTotal.setText("Total: Rp " + total);
    }
}
