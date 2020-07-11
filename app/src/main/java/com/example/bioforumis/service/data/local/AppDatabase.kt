package com.example.bioforumis.service.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bioforumis.service.data.remote.Apod

@Database(entities = arrayOf(LocalApod::class), version = 1,exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apodDao(): ApodDao
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase::class.java, "chapter.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}