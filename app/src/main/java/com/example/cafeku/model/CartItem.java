package com.example.cafeku.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_table")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public int price;
    public String image;
    public int quantity;

    public CartItem(String name, int price, String image, int quantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }
}
