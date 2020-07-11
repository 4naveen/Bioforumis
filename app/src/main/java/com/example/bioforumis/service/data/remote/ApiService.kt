package com.example.bioforumis.service.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface  ApiService {
    @GET("planetary/apod")
     fun getApod(@Query("api_key") api_key:String,@Query("start_date") start_date:String,@Query("end_date") end_date:String):Single<List<Apod>>


    @GET("planetary/apod")
     fun getApodBydate(@Query("api_key") api_key:String,@Query("date") date:String):Single<Apod>



}