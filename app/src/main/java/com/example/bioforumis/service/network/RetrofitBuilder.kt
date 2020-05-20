package com.example.bioforumis.service.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit;

class RetrofitBuilder {

    companion object RetrofitBuilder {

        private const val BASE_URL = "https://api.nasa.gov/"

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                .build() //Doesn't require the adapter
        }

        val apiService: ApiService = getRetrofit().create(ApiService::class.java)
    }
}