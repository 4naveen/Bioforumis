package com.example.bioforumis.service.model

import com.google.gson.annotations.SerializedName

data class Apod(


    @SerializedName("url")
    val image_url: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("title")
    val title: String
)