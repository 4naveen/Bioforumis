package com.example.bioforumis.service.data.local

import androidx.room.*
import com.example.bioforumis.service.data.remote.Apod
import io.reactivex.Single

@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApod(apod: LocalApod)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( apods: List<LocalApod>)

    @Query("SELECT * FROM table_apod")
    fun getApods(): List<LocalApod>

    @Query("SELECT * FROM table_apod")
    fun getAll(): Single <List<LocalApod>>

    @Transaction
    open fun updateData(users: List<LocalApod>) {
        deleteAllApod()
        insertAll(users)
    }

    @Query("DELETE FROM table_apod")
    abstract fun deleteAllApod()


}