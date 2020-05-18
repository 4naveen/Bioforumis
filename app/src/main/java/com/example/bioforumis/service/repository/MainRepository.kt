package com.example.bioforumis.service.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.bioforumis.service.model.Apod
import com.example.bioforumis.service.model.ApodEntity
import com.example.bioforumis.service.model.AppDatabase
import com.example.bioforumis.service.network.ApiService
import com.example.bioforumis.service.network.RetrofitBuilder
import com.example.bioforumis.service.utils.GeneralService
import com.example.bioforumis.service.utils.Response
import com.example.bioforumis.service.utils.Status
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback


class MainRepository() {
    private var apiservice: ApiService? = null

    private var _apodList: MutableLiveData<Response<List<Apod>>> = MutableLiveData()
    var apodList: LiveData<Response<List<Apod>>> =_apodList
    var db:AppDatabase?=null

    private var _apod: MutableLiveData<Response<Apod>> = MutableLiveData()
    var apod: LiveData<Response<Apod>> =_apod

   init {
       apiservice = RetrofitBuilder.apiService
   }

    fun getApods() {

        apiservice?.getApod("NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo",GeneralService.getStartDate(),GeneralService.getEnddate())?.enqueue(object : Callback<List<Apod>?>{
            override fun onFailure(call: Call<List<Apod>?>, t: Throwable) {
                _apodList.value= Response(Status.ERROR, null, "")
            }

            override fun onResponse(call: Call<List<Apod>?>, response: retrofit2.Response<List<Apod>?>) {
                _apodList.value= Response(Status.SUCCESS, response.body(), "")


            }
        })
    }

    fun getApod(date:String) {

        apiservice?.getApodBydate("NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo",date)?.enqueue(object : Callback<Apod>{
            override fun onFailure(call: Call<Apod>, t: Throwable) {
                _apod.value= Response(Status.ERROR, null, "")
            }

            override fun onResponse(call: Call<Apod>, response: retrofit2.Response<Apod>) {
                _apod.value= Response(Status.SUCCESS, response.body(), "")
            }
        })
    }

    fun saveApod(apods:ArrayList<Apod>,context:Context) {
        db = Room.databaseBuilder(context,AppDatabase::class.java, "apod.db").build()
        var apod_list:ArrayList<ApodEntity>
        GlobalScope.launch {
            db!!.apodDao().insertAll(apods)
            var data = db!!.apodDao().getAll()

            data?.forEach {
                Log.e("data",it.date)
            }
        }

    }
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