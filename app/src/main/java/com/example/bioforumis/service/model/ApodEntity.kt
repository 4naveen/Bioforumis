package com.example.bioforumis.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_apod")
data class ApodEntity(@PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "image_url") var image_url: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "title") var title: String
)