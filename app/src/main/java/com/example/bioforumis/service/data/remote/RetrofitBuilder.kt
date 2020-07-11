package com.example.bioforumis.service.data.remote

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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