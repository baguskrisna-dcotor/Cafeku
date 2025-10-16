package com.example.cafeku.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cart_table")
public class CartItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public int point;

    public int price;
    public String image;
    public int quantity;

    public CartItem(String name, int point,int price, String image, int quantity) {
        this.name = name;
        this.point = point;
        this.price = price;
        this.image = image;
        this.quantity = quantity;


    }
}
