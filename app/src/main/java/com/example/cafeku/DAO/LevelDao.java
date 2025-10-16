package com.example.cafeku.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cafeku.model.LevelModel;

import java.util.List;

@Dao
public interface LevelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LevelModel levelModel);

    @Update
    void update(LevelModel levelModel);

    @Query("SELECT * FROM Level_table WHERE Level = :level LIMIT 1")
    LevelModel getLevelById(int level);

    @Query("SELECT Level FROM Level_table")
    List<Integer> getAllPointLevels();
}

