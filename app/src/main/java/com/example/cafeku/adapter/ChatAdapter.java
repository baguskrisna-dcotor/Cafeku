package com.example.cafeku.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeku.DAO.ImgDao;
import com.example.cafeku.DAO.UserDao;
import com.example.cafeku.R;
import com.example.cafeku.database.ImgDatabase;
import com.example.cafeku.database.UserDatabase;
import com.example.cafeku.model.Chat;
import com.example.cafeku.model.Img;
import com.example.cafeku.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context context;
    private List<Chat> chatList;
    private OnDeleteClickListener listener;

    private UserDatabase ub;
    private ImgDatabase ib;

    public interface OnDeleteClickListener {
        void onDelete(Chat chat);
    }

    public ChatAdapter(Context context, List<Chat> chatList, OnDeleteClickListener listener) {
        this.context = context;
        this.chatList = chatList;
        this.listener = listener;
        ib = ImgDatabase.getInstance(context);
        ub = UserDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        UserDao p = ub.userDao();
        User username = p.getUser();
        ImgDao i = ib.imgDao();
        Img photouser = i.select();

        if (photouser != null && photouser.img != null) {
            try {
                InputStream is2 = context.getAssets().open("imageprofile/" + photouser.img + ".png");
                Bitmap bmp = BitmapFactory.decodeStream(is2);
                holder.profile.setImageBitmap(bmp);
                is2.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("IMG_LOAD", "Gagal memuat gambar: " + photouser.img, e);
                holder.profile.setImageResource(R.drawable.icon_guest);
            }
        } else {
            holder.profile.setImageResource(R.drawable.icon_guest);
        }

        if (username != null && username.username != null){
            holder.tvName.setText(username.username);
        }else{
            holder.tvName.setText(String.valueOf(chat.id));
        }
        String currentDate = new SimpleDateFormat("EEEE, dd MMMM yyyy").format(new Date());
        holder.tvdate.setText(currentDate);
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format((new Date()));
        holder.tvtime.setText(time);
        holder.tvChat.setText(chat.chat);
        holder.ratingBar.setRating(chat.rating);
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(chat));

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvChat,tvdate,tvtime;
        RatingBar ratingBar;
        Button btnDelete;

        ImageView profile;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtime = itemView.findViewById(R.id.tvTime);
            tvdate = itemView.findViewById(R.id.tvDate);
            tvName = itemView.findViewById(R.id.tvName);
            tvChat = itemView.findViewById(R.id.tvChat);
            ratingBar = itemView.findViewById(R.id.ratingBarItem);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            profile = itemView.findViewById(R.id.photoprofile);
        }
    }
}
