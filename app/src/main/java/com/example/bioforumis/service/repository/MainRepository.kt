package com.example.bioforumis.service.repository

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.bioforumis.service.model.data.Apod
import com.example.bioforumis.service.model.data.Response
import com.example.bioforumis.service.model.utils.AppDatabase
import com.example.bioforumis.service.model.utils.GeneralService
import com.example.bioforumis.service.network.ApiService
import com.example.bioforumis.service.network.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback


class MainRepository() {
    private var apiservice: ApiService? = null
    private val disposable = CompositeDisposable()
    private var _apodList: MutableLiveData<Response<List<Apod>>> = MutableLiveData()
    var apodList: LiveData<Response<List<Apod>>> = _apodList
    var db: AppDatabase? = null

    private var _apod: MutableLiveData<Response<Apod>> = MutableLiveData()
    var apod: LiveData<Response<Apod>> = _apod

    init {
        apiservice = RetrofitBuilder.apiService

    }

    fun getApods(context: Context) {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "apod.db").build()
        disposable.add(
            apiservice?.getApod("NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo",GeneralService.getStartDate(), GeneralService.getEnddate())?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribeWith(object : DisposableSingleObserver<List<Apod?>?>() {
                override fun onSuccess(apods: List<Apod?>) {

                    _apodList.value = Response(Response.Status.SUCCESS,apods as List<Apod>,"")

                    try {
                        GlobalScope.launch {
                            db!!.apodDao().updateData(apods as List<Apod>)
                        }
                    } catch (e: Exception) {
                        _apodList.value = Response(Response.Status.ERROR, null, "")
                    }
                }
                override fun onError(e: Throwable) {
                    _apodList.value = Response(Response.Status.ERROR, null, "")
                }
            })!!
        );
    }
    fun getApodsfromdb(context: Context) {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "apod.db").build()

        try {
            Thread(Runnable {
                var data = db!!.apodDao().getAll()

                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    _apodList.value =
                        Response(
                            Response.Status.SUCCESS,
                            data,
                            ""
                        )
                }

                data.forEach {
                    Log.e("data", it.date)
                }
            }).start()
        } catch (e: Exception) {
            _apodList.value = Response(
                Response.Status.ERROR,
                null,
                ""
            )

        }

    }

    fun getApod(date: String) {

        disposable.add(
            apiservice?.getApodBydate("NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo",date)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribeWith(object : DisposableSingleObserver<Apod?>() {
                override fun onSuccess(apod: Apod) {

                    _apod.value = Response(Response.Status.SUCCESS, apod, "")


                }

                override fun onError(e: Throwable) {
                    _apodList.value = Response(Response.Status.ERROR, null, "")
                }
            })!!
        );
    }
  fun destroyDisposableObject(){
      disposable.dispose();
  }
    companion object {
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