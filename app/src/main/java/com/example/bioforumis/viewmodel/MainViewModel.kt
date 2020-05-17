package com.example.bioforumis.viewmodel

import android.bluetooth.BluetoothClass.Device
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.bioforumis.service.model.Apod
import com.example.bioforumis.service.repository.MainRepository
import com.example.bioforumis.service.utils.Response
import com.example.bioforumis.service.utils.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.*


class MainViewModel : ViewModel() {
    private var _apodList: MutableLiveData<Response<List<Apod>>> =MutableLiveData()
    var apodList: LiveData<Response<List<Apod>>> = _apodList

    init {
        _apodList.value = Response(Status.UNKNOWN, null, "")
    }

    fun getApods() = liveData(Dispatchers.IO) {
        emit(Response.loading(data = null))
        try {
            emit(Response.success(data = MainRepository.getInstance()!!.getApods()))
        } catch (exception: Exception) {
            emit(Response.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
    fun getApods1() {
        //THis is a sample comment
        var value=_apodList.value
        value?.status=Status.LOADING

        CoroutineScope(Dispatchers.IO).launch {
            val response = MainRepository.getInstance()!!.getApods()

            if (!response.isEmpty()) {
                Log.e("sizevv", response.size.toString())
                var value = _apodList.value
                value?.data = response
                value?.status = Status.SUCCESS

            } else {
                var value = _apodList.value
                value?.data = null
                value?.status = Status.ERROR
            }
        }

    }

    fun getApod(date :String) = liveData(Dispatchers.IO) {
        emit(Response.loading(data = null))
        try {
            emit(Response.success(data = MainRepository.getInstance()!!.getApod(date)))
        } catch (exception: Exception) {
            emit(Response.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}