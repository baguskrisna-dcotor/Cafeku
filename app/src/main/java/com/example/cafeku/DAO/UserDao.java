package com.example.cafeku.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cafeku.model.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User getUser(String username);

    @Query("SELECT * FROM users WHERE gender= :gender LIMIT 1")
    User getGender(Boolean gender);

    @Delete
    void  Logout(User user);
}
