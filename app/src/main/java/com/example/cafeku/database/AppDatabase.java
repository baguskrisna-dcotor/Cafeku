package com.example.cafeku.database;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cafeku.DAO.CartDao;
import com.example.cafeku.model.CartItem;

@Database(entities = {CartItem.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract CartDao cartDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "cart_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // bisa pakai coroutine/livedata di tahap lanjut
                    .build();
        }
        return instance;
    }
}
