package com.example.cafeku.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.cafeku.database.PointDatabase;
import com.example.cafeku.model.CartItem;
import com.example.cafeku.model.Point;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private AppDatabase db;

    private PointDatabase dbpoint;
    private OnTotalChangeListener listener;

    public interface OnTotalChangeListener {
        void onTotalChanged(double total,int totalpoint);
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnTotalChangeListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
        db = AppDatabase.getInstance(context);
        dbpoint = PointDatabase.getInstance(context);
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
         holder.txtpoint.setText(String.valueOf(item.point));


        try {
            InputStream inputStream = context.getAssets().open( item.image );
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.imageView.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            int resId = context.getResources().getIdentifier(item.image, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.imageView.setImageResource(resId);
            } else {
                holder.imageView.setImageResource(R.drawable.dummy); // placeholder
            }
        }
        holder.btnAdd.setOnClickListener(v -> {
            item.quantity++;
            db.cartDao().update(item);
            notifyItemChanged(position);
            listener.onTotalChanged(getTotal(),getTotalpoint());
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
            listener.onTotalChanged(getTotal(),getTotalpoint());
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

    private int getTotalpoint(){
        int totalpoint = 0;
        for (CartItem i : cartItems){
            totalpoint += i.point * i.quantity;
        }
        return totalpoint;
    }
    public void clear() {
        db.cartDao().DeleteAll();
        cartItems.clear();
        notifyDataSetChanged();
        listener.onTotalChanged(0,getTotalpoint());
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtName, txtPrice, txtQuantity,txtpoint;
        ImageButton btnAdd, btnMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgCartItem);
            txtName = itemView.findViewById(R.id.txtCartName);
            txtpoint = itemView.findViewById(R.id.txtCartpoint);
            txtPrice = itemView.findViewById(R.id.txtCartPrice);
            txtQuantity = itemView.findViewById(R.id.txtCartQty);
            btnAdd = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }

        }
    }

