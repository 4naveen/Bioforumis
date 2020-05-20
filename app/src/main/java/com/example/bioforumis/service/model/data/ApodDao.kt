package com.example.bioforumis.service.model.data

import androidx.room.*
import com.example.bioforumis.service.model.data.Apod

@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApod(apod: Apod)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( apods: List<Apod>)

    @Query("SELECT * FROM table_apod")
    fun getApods(): List<Apod>

    @Query("SELECT * FROM table_apod")
    fun getAll(): List<Apod>

    @Transaction
    open fun updateData(users: List<Apod>) {
        deleteAllApod()
        insertAll(users)
    }

    @Query("DELETE FROM table_apod")
    abstract fun deleteAllApod()
}