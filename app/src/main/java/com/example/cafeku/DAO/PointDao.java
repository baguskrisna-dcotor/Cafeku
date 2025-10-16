package com.example.cafeku.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cafeku.model.Point;

import java.util.List;

@Dao
public interface PointDao {

    @Insert
    void insert(Point point);

    @Update
    void update(Point point);

    @Query("DELETE FROM point_table")
    void deleteAll();

    @Query("SELECT * FROM point_table LIMIT 1")
    Point getPoints();

    @Query("UPDATE point_table SET totalPoint = :newtotal WHERE id = :id")
    void updateTotalPoint(int id, int newtotal);

    @Query("UPDATE point_table SET totalPoint = totalPoint + :value WHERE id = :id")
    void addpoint (int id,int value);
}
