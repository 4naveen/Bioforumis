package com.example.bioforumis.service.repository

import com.example.bioforumis.service.network.ApiService
import com.example.bioforumis.service.network.RetrofitBuilder
import com.example.bioforumis.service.utils.GeneralService


class MainRepository() {
    private var apiservice: ApiService? = null

   init {
       apiservice = RetrofitBuilder.apiService
   }

    suspend fun getApods() = apiservice!!.getApod("NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo",GeneralService.getStartDate(),GeneralService.getEnddate())
    suspend fun getApod(date:String) = apiservice!!.getApodBydate("NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo",date)


  companion object{
      private var mainRepository: MainRepository? = null

      @Synchronized
      fun getInstance(): MainRepository? {
          if (mainRepository == null) {
              if (mainRepository == null) {
                  mainRepository = MainRepository()
              }
          }
          return mainRepository
      }
  }





}