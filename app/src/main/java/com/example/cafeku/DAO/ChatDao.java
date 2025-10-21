package com.example.cafeku.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.cafeku.model.CartItem;
import com.example.cafeku.model.Chat;

import java.util.List;

@Dao
public interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Chat chat);

    @Query("SELECT * FROM chat_table")
    List<Chat> getAllItems(); // sebelumnya kamu salah: List<CartItem>

    @Query("UPDATE chat_table SET  chat= :chat WHERE id = :id")
    void update(String chat,int id);

    @Query("DELETE FROM chat_table")
    void DeleteAll();

    @Query("UPDATE chat_table SET rating= :rating WHERE id =:id ")
    void updaterating(int id ,int rating);

    @Query("DELETE FROM chat_table WHERE id = :id")
    void deleteById(int id);

}
