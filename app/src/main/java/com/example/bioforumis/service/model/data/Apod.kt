package com.example.bioforumis.service.model.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_apod")
data class Apod(
    @ColumnInfo
    @SerializedName("url")
    val image_url: String,
    @ColumnInfo
    @SerializedName("date")
    val date: String,
    @PrimaryKey
    @SerializedName("title")
    val title: String
)