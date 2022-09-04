package com.example.bioforumis.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.bioforumis.service.data.remote.Response
import com.example.bioforumis.service.repository.MainRepository
import com.example.bioforumis.service.data.remote.Apod
import com.example.bioforumis.service.utils.GeneralService


class MainViewModel(application :Application) : AndroidViewModel(application) {
    var isConnected: Boolean = false
    private val repository: MainRepository
    var apodList: LiveData<Response<List<Apod>>>
    var apod: LiveData<Response<Apod>>
    init {
        repository = MainRepository(application)
         apodList= repository.apodList
        apod = repository.apod
    }

    fun getApods() {
        val res = apodList.value
        res?.status = Response.Status.LOADING
        isConnected=GeneralService.isOnline(getApplication())
        if (isConnected) {
            repository.getApods()
        }
        else{
            repository.getApodsfromdb()
        }
    }

    fun getApod(date: String) {
        val res = apod.value
        res?.status = Response.Status.LOADING
        repository.getApod(date)
    }
    fun deleteDisposable(){
        repository.destroyDisposableObject()

    }
}