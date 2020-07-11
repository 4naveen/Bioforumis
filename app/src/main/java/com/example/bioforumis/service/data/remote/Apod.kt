package com.example.bioforumis.service.data.remote


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Apod(
    @SerializedName("url")
    val image_url: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("title")
    val title: String
)