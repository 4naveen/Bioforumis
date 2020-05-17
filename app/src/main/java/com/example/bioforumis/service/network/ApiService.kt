package com.example.bioforumis.service.network

import com.example.bioforumis.service.model.Apod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface  ApiService {
    @GET("planetary/apod")
    suspend fun getApod(@Query("api_key") api_key:String,@Query("start_date") start_date:String,@Query("end_date") end_date:String):List<Apod>


    @GET("planetary/apod")
    suspend fun getApodBydate(@Query("api_key") api_key:String,@Query("date") date:String):Apod

}