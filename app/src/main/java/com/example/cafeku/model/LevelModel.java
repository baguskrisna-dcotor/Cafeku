package com.example.cafeku.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Level_table")
public class LevelModel implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Level")
    public int Level;

    @ColumnInfo(name = "LevelName")
    public String LevelName;

    @ColumnInfo(name = "MinPoint")
    public int MinPoint;

    public LevelModel(int Level, String LevelName, int MinPoint) {
        this.Level = Level;
        this.LevelName = LevelName;
        this.MinPoint = MinPoint;
    }

    public LevelModel() {}
}

