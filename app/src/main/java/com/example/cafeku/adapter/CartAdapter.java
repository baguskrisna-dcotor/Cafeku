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

    private Context context;          // untuk akses resource dan layout
    private List<CartItem> cartItems; // data keranjang yang akan ditampilkan
    private AppDatabase db;           // database Room untuk data Cart
    private PointDatabase dbpoint;    // database untuk point user
    private OnTotalChangeListener listener; // interface callback untuk update total harga


    public interface OnTotalChangeListener {
        void onTotalChanged(double total,int totalpoint);
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnTotalChangeListener listener) {
        this.context = context;//context untuk akses ke resource/layout
        this.cartItems = cartItems;//cartItems yaitu daftar produk di keranjang
        this.listener = listener;//listener untuk mengirimkan event ke activity
        db = AppDatabase.getInstance(context);//koneksi ke data base untuk mengambil data
        dbpoint = PointDatabase.getInstance(context);//koneksi ke database untuk mengambil data
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);//inflate() = buat tampilan item
        // berdasarkan file XML item_cart.xml
        return new CartViewHolder(view);//Bungkus tampilan itu ke dalam CartViewHolder.
       // Return ke RecyclerView agar bisa digunakan.
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);//Ambil satu data produk (CartItem) dari daftar cartItems,
        // berdasarkan posisi (index) yang sedang ditampilkan di RecyclerView
//      set isi text dari data base produk
        holder.txtName.setText(item.name);
        holder.txtPrice.setText("Rp " + item.price);
        holder.txtQuantity.setText(String.valueOf(item.quantity));
         holder.txtpoint.setText(String.valueOf(item.point));

//      cari gambar yang sesuai dengan nama gambar dari database , kalau tidak ada cari dari drawable kalau tidak ada set image menjaid dummy
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
//Tambah jumlah item di database (quantity += 1).
//
//Perbarui tampilan item di RecyclerView.
        holder.btnAdd.setOnClickListener(v -> {
            item.quantity++;
            db.cartDao().update(item);
            notifyItemChanged(position);
            listener.onTotalChanged(getTotal(),getTotalpoint());//Hitung ulang total harga dan poin, lalu beri tahu activity lewat listener.
        });
//Kalau jumlah > 1 → kurangi 1 dan update database.
//
//Kalau jumlah sudah 1 → hapus item dari database dan list.
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
            listener.onTotalChanged(getTotal(),getTotalpoint());//Setelah itu panggil listener untuk update total harga/poin di UI utama.
        });
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }
    //Hitung Total Harga dan Total Point
    public double getTotal() {
        double total = 0;
        for (CartItem i : cartItems) {
            total += i.price * i.quantity;
        }
        return total;
    }

    public int getTotalpoint(){
        int totalpoint = 0;
        for (CartItem i : cartItems){
            totalpoint += i.point * i.quantity;
        }
        return totalpoint;
    }


    public double discon(double d,double total){
        double diskon = d * total;//d = nilai diskon (contoh 0.2 = 20%).
                                            //total = total harga sebelum diskon.
        double totaldiskon = total-diskon;//Hasilnya = total harga setelah dikurangi diskon.

        return  totaldiskon;
    }
    public void clear() {
        db.cartDao().DeleteAll(); // hapus semua dari database
        cartItems.clear();        // hapus semua dari list di memory
        notifyDataSetChanged();   // perbarui tampilan RecyclerView
        listener.onTotalChanged(0, getTotalpoint()); // update total di UI jadi 0
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        //ViewHolder menyimpan semua komponen tampilan per item
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
//Fungsi	Penjelasan
//Adapter	Menghubungkan data dengan tampilan (RecyclerView)
//ViewHolder	Menyimpan view tiap item agar efisien
//onCreateViewHolder	Buat tampilan item dari XML
//onBindViewHolder	Isi tampilan dengan data produk
//getItemCount	Jumlah item di daftar
//getTotal / getTotalpoint	Hitung total harga dan poin
//clear	Hapus semua item dari keranjang
//listener.onTotalChanged	Mengirim perubahan total ke UI utama
