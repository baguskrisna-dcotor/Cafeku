package com.example.cafeku;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context context;
    private List<MenuItem> menuList;

    public MenuAdapter(Context context, List<MenuItem> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        MenuItem item = menuList.get(position);

        // set nama menu
        holder.tvMenu.setText(item.getName());
        holder.tvprice.setText(item.getPrice());


        // load gambar dari assets
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open("coffemenu/" + item.getImageFileName()); // pastikan folder dan nama file benar
            Drawable d = Drawable.createFromStream(is, null);
            holder.imgMenu.setImageDrawable(d);
            is.close(); // jangan lupa close
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMenu;
        TextView tvMenu,tvprice;

        public MenuViewHolder(View itemView) {
            super(itemView);
            imgMenu = itemView.findViewById(R.id.imgMenu);
            tvMenu = itemView.findViewById(R.id.tvMenu);
            tvprice = itemView.findViewById(R.id.tvprice);
        }
    }
}
