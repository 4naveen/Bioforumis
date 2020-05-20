package com.example.bioforumis.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bioforumis.service.model.data.Apod
import com.example.bioforumis.service.repository.MainRepository
import com.example.bioforumis.service.model.data.Response


class MainViewModel : ViewModel() {
    var apodList: LiveData<Response<List<Apod>>> = MainRepository.getInstance()!!.apodList
    var apod: LiveData<Response<Apod>> = MainRepository.getInstance()!!.apod

    fun getApods(context: Context, isConnected: Boolean) {
        val res = apodList.value
        res?.status = Response.Status.LOADING
        if (isConnected) {
            MainRepository.getInstance()!!.getApods(context)
        }
        else{
            MainRepository.getInstance()!!.getApodsfromdb(context)
        }
    }

    fun getApod(date: String) {
        val res = apod.value
        res?.status = Response.Status.LOADING
        MainRepository.getInstance()!!.getApod(date)
    }
    fun deleteDisposable(){
        MainRepository.getInstance()!!.destroyDisposableObject()

    }
}