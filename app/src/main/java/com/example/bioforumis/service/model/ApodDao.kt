package com.example.bioforumis.service.model

import androidx.room.*

@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApod(apod: Apod)

    @Insert
    fun insertAll( apods: List<Apod>)

    @Query("SELECT * FROM table_apod")
    fun getApods(): List<Apod>

    @Query("SELECT * FROM table_apod")
    fun getAll(): List<Apod>
}