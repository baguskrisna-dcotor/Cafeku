package com.example.cafeku.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chat_table")
public class Chat {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String chat;

    public int rating;
    public Chat(int id,String chat,int rating) {
        this.chat = chat;
        this.id = id;
        this.rating = rating;
    }
}
