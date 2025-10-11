package com.example.cafeku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeku.R;
import com.example.cafeku.database.AppDatabase;
import com.example.cafeku.model.CartItem;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private AppDatabase db;
    private OnTotalChangeListener listener;

    public interface OnTotalChangeListener {
        void onTotalChanged(double total);
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnTotalChangeListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
        db = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.txtName.setText(item.name);
        holder.txtPrice.setText("Rp " + item.price);
        holder.txtQuantity.setText(String.valueOf(item.quantity));

        int resId = context.getResources().getIdentifier(item.image, "drawable", context.getPackageName());
        if (resId != 0) {
            holder.imageView.setImageResource(resId);
        }

        holder.btnAdd.setOnClickListener(v -> {
            item.quantity++;
            db.cartDao().update(item);
            notifyItemChanged(position);
            listener.onTotalChanged(getTotal());
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (item.quantity > 1) {
                item.quantity--;
                db.cartDao().update(item);
                notifyItemChanged(position);
            } else {
                db.cartDao().delete(item);
                cartItems.remove(position);
                notifyItemRemoved(position);
            }
            listener.onTotalChanged(getTotal());
        });
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private double getTotal() {
        double total = 0;
        for (CartItem i : cartItems) {
            total += i.price * i.quantity;
        }
        return total;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtName, txtPrice, txtQuantity;
        ImageButton btnAdd, btnMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgCartItem);
            txtName = itemView.findViewById(R.id.txtCartName);
            txtPrice = itemView.findViewById(R.id.txtCartPrice);
            txtQuantity = itemView.findViewById(R.id.txtCartQty);
            btnAdd = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}
