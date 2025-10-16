package com.example.cafeku.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cafeku.DAO.PointDao;
import com.example.cafeku.model.Point;

@Database(entities = {Point.class}, version = 1, exportSchema = false)
public abstract class PointDatabase extends RoomDatabase {

    private static volatile PointDatabase INSTANCE;

    public abstract PointDao pointDao();

    public static PointDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PointDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    PointDatabase.class,
                                    "point_db"
                            )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries() // gunakan coroutine/async kalau mau lebih baik
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
