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

    @Query("UPDATE users SET username = :newName ")
    void updateNama( String newName);
    @Query("SELECT * FROM users LIMIT 1")
    User getUser();


    @Query("SELECT * FROM users WHERE gender= :gender LIMIT 1")
    User getGender(Boolean gender);

    @Query("DELETE FROM users")
    void logout();
}
