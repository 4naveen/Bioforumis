package com.example.bioforumis.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.bioforumis.service.model.Apod
import com.example.bioforumis.service.model.ApodEntity
import com.example.bioforumis.service.repository.MainRepository
import com.example.bioforumis.service.utils.Response
import com.example.bioforumis.service.utils.Status
import kotlinx.coroutines.Dispatchers


class MainViewModel : ViewModel() {
    var apodList: LiveData<Response<List<Apod>>> =MainRepository.getInstance()!!.apodList
    var apod: LiveData<Response<Apod>> =MainRepository.getInstance()!!.apod

    fun getApods(){
        val res = apodList.value
        res?.status = Status.LOADING
        MainRepository.getInstance()!!.getApods()
    }

    fun getApod(date:String){
        val res = apod.value
        res?.status = Status.LOADING
        MainRepository.getInstance()!!.getApod(date)
    }
    fun saveApod(apods:ArrayList<Apod>,context:Context){

        MainRepository.getInstance()!!.saveApod(apods,context)
    }
}