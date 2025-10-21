package com.example.cafeku.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cafeku.DAO.UserDao;
import com.example.cafeku.model.User;

@Database(entities = {User.class}, version = 2)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance; //
    public abstract UserDao userDao();

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            UserDatabase.class,
                            "cafeku_db"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    // boleh untuk tes
                    .build();
        }
        return instance;
    }
}
