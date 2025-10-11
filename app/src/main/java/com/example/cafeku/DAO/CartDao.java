package com.example.cafeku.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.cafeku.model.CartItem;

import java.util.List;

@Dao
public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartItem cartItem);

    @Query("SELECT * FROM cart_table")
    List<CartItem> getAllItems();

    @Query("SELECT * FROM cart_table WHERE name = :name LIMIT 1")
    CartItem getItemByName(String name);

    @Update
    void update(CartItem cartItem);

    @Delete
    void delete(CartItem cartItem);

    @Query("DELETE FROM cart_table")
    void clearCart();
}
