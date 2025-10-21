package com.example.cafeku;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeku.adapter.ChatAdapter;
import com.example.cafeku.database.ChatDatabase;
import com.example.cafeku.model.Chat;
import com.example.cafeku.R;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerChat;
    private EditText etChat;
    private TextView bigtext;
    private LinearLayout btnSend;
    private RatingBar ratingBar;
    private ChatDatabase db;
    private ChatAdapter adapter;
    private List<Chat> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerChat = findViewById(R.id.recyclerChat);
        etChat = findViewById(R.id.etChat);
        btnSend = findViewById(R.id.btnSend);
        ratingBar = findViewById(R.id.ratingBarInput);
        bigtext = findViewById(R.id.commentbigtext);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.updown);
        bigtext.setAnimation(animation);

        db = ChatDatabase.getInstance(this);
        loadChat();

        btnSend.setOnClickListener(v -> {
            String message = etChat.getText().toString().trim();
            int rating = (int) ratingBar.getRating();

            if (message.isEmpty()) {
                Toast.makeText(this, "Komentar tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            Chat newChat = new Chat(0, message, rating);
            db.chatDao().insert(newChat);
            etChat.setText("");
            ratingBar.setRating(0);
            loadChat();
        });
    }

    private void loadChat() {
        chatList = db.chatDao().getAllItems();

        adapter = new ChatAdapter(this, chatList, chat -> {
            db.chatDao().deleteById(chat.id);
            Toast.makeText(this, "Komentar dihapus", Toast.LENGTH_SHORT).show();
            loadChat();
        });

        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerChat.setAdapter(adapter);
    }

}
