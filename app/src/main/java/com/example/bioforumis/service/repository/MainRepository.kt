package com.example.bioforumis.service.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.bioforumis.service.data.remote.Apod
import com.example.bioforumis.service.data.remote.Response
import com.example.bioforumis.service.data.local.AppDatabase
import com.example.bioforumis.service.data.local.LocalApod
import com.example.bioforumis.service.utils.GeneralService
import com.example.bioforumis.service.data.remote.ApiService
import com.example.bioforumis.service.data.remote.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.ArrayList


class MainRepository(application :Application ) {
    private var apiservice: ApiService? = null
    private val disposable = CompositeDisposable()
    private var _apodList: MutableLiveData<Response<List<Apod>>> = MutableLiveData()
    var apodList: LiveData<Response<List<Apod>>> = _apodList
    var db: AppDatabase? = null

    private var _apod: MutableLiveData<Response<Apod>> = MutableLiveData()
    var apod: LiveData<Response<Apod>> = _apod

    init {
        apiservice = RetrofitBuilder.apiService
        db = Room.databaseBuilder(application, AppDatabase::class.java, "apod.db").build()

    }

    fun getApods() {
        disposable.add(
            apiservice!!.getApod("NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo",GeneralService.getStartDate(), GeneralService.getEnddate()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : DisposableSingleObserver<List<Apod>>() {
                override fun onSuccess(apods: List<Apod>) {
                    _apodList.value =
                        Response(Response.Status.SUCCESS, apods as List<Apod>, ""
                        )
                    try {
                        GlobalScope.launch {
                            var local_apods:ArrayList<LocalApod>?=null
                            for (apod in apods){
                                var localApod:LocalApod=LocalApod(apod.image_url,apod.date,apod.title)
                                local_apods!!.add(localApod)
                            }
                            db!!.apodDao().updateData(local_apods as List<LocalApod>)
                        }
                    } catch (e: Exception) {
                        _apodList.value =
                            Response(
                                Response.Status.ERROR,
                                null,
                                ""
                            )
                    }
                }
                override fun onError(e: Throwable) {
                    _apodList.value =
                        Response(
                            Response.Status.ERROR, null, e.message
                        )
                }
            })!!
        );
    }
    fun getApodsfromdb() {
        disposable.add(
            db!!.apodDao().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : DisposableSingleObserver<List<LocalApod>>() {
                override fun onSuccess(apods: List<LocalApod>) {
                    var apodList:ArrayList<Apod>?=null
                    for (apod in apods){
                        var apod:Apod=Apod(apod.image_url,apod.date,apod.title)
                        apodList!!.add(apod)
                    }
                    _apodList.value = Response(Response.Status.SUCCESS, apodList, "")
                }
                override fun onError(e: Throwable) { _apodList.value = Response(Response.Status.ERROR, null, "") }
            })!!
        );
    }

    fun getApod(date: String) {

        disposable.add(
            apiservice?.getApodBydate("NNKOjkoul8n1CH18TWA9gwngW1s1SmjESPjNoUFo",date)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribeWith(object : DisposableSingleObserver<Apod?>() {
                override fun onSuccess(apod: Apod) {

                    _apod.value = Response(
                        Response.Status.SUCCESS,
                        apod,
                        ""
                    )


                }

                override fun onError(e: Throwable) {
                    _apodList.value =
                        Response(
                            Response.Status.ERROR,
                            null,
                            ""
                        )
                }
            })!!
        );
    }
  fun destroyDisposableObject(){
      disposable.dispose();
  }


}