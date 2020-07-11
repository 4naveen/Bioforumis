package com.example.bioforumis.service.data.local


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_apod")
data class LocalApod(
    @ColumnInfo
    val image_url: String,
    @ColumnInfo
    var date: String,
    @PrimaryKey
    val title: String
)