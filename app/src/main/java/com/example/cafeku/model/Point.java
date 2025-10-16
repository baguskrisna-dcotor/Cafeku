package com.example.cafeku.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "point_table")
public class Point {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int totalPoint;
    public Point() {}

    public Point(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Point(int id,int totalPoint) {
        this.totalPoint = totalPoint;
        this.id = id;
    }
}
