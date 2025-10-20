package com.example.cafeku.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cafeku.DAO.ImgDao;
import com.example.cafeku.model.Img;

@Database(entities = {Img.class}, version = 1, exportSchema = false)
public abstract class ImgDatabase extends RoomDatabase {

    private static volatile ImgDatabase INSTANCE;

    public abstract ImgDao imgDao();

    public static ImgDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ImgDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ImgDatabase.class,
                                    "img_db"
                            )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
