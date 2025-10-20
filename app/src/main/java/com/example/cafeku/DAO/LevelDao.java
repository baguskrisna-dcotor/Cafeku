package com.example.cafeku.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cafeku.model.LevelModel;

import java.util.List;
import java.util.logging.Level;

@Dao
public interface LevelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LevelModel levelModel);

    @Query("DELETE FROM level_table")
    void delete();

    @Update
    void update(LevelModel levelModel);

    @Query("SELECT * FROM level_table WHERE Level = :level LIMIT 1")
    LevelModel getLevelById(int level);

    @Query("SELECT Level FROM Level_table")
    List<Integer> getAllPointLevels();

    @Query("SELECT * FROM level_table LIMIT 1")
    Level select();
}

