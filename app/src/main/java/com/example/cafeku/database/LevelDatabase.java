package com.example.cafeku.database;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cafeku.DAO.CartDao;
import com.example.cafeku.DAO.LevelDao;
import com.example.cafeku.model.CartItem;
import com.example.cafeku.model.LevelModel;

@Database(entities = {LevelModel.class}, version = 1)
public abstract class LevelDatabase extends RoomDatabase {
    private static LevelDatabase instance;

    public abstract LevelDao levelDao();

    public static synchronized LevelDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            LevelDatabase.class, "level_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
