package com.example.cafeku.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "gender")
    public Boolean gender;

    public User(String username, String password,Boolean gender) {
        this.username = username;
        this.password = password;
        this.gender = gender;
    }
}
