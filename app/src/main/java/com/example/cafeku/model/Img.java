package com.example.cafeku.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "img_table")
public class Img {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String img;
    public Img() {}

    public Img(String img) {
        this.img = img;
    }

    public Img(int id,String img) {
        this.img = img;
        this.id = id;
    }
}
