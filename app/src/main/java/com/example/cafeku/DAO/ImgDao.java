package com.example.cafeku.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cafeku.model.Img;

@Dao
public interface ImgDao {

    // Menyimpan gambar baru
    @Insert
    void insert(Img img);

    // Mengambil satu data gambar (misal untuk foto profil)
    @Query("SELECT * FROM img_table LIMIT 1")
    Img select();

    // Update nama gambar (misal saat ganti foto)
    @Query("UPDATE img_table SET img = :name WHERE id = :id")
    void update(int id, String name);

    @Query("DELETE FROM img_table")
    void delete();
}
