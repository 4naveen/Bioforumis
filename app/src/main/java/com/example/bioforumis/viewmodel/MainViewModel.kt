package com.example.bioforumis.viewmodel

import android.bluetooth.BluetoothClass.Device
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


class MainViewModel : ViewModel() {
    private var _apodList: MutableLiveData<Response<List<Apod>>> =MutableLiveData()
    var apodList: LiveData<Response<List<Apod>>> = _apodList

    init {
        var value=apodList.value
        value?.status=Status.UNKNOWN
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
        var value=apodList.value
        value?.status=Status.LOADING

        CoroutineScope(Dispatchers.IO).launch {
            val response = MainRepository.getInstance()!!.getApods()
            withContext(Dispatchers.Main) {
                try {
                    if (!response.isEmpty()) {
                        Log.e("sizevv",response.size.toString())
                         var value=apodList.value
                        value?.data=response
                        value?.status=Status.SUCCESS



                    } else {
                        var value=apodList.value
                        value?.data=null
                        value?.status=Status.ERROR
                       // GeneralService.showToast(this@MainActivity,"Error: ${response}")
                    }
                } catch (e: HttpException) {
                    var value=apodList.value
                    value?.data=null
                    value?.status=Status.ERROR
                    //toast("Exception ${e.message}")
                } catch (e: Throwable) {
                    var value=apodList.value
                    value?.data=null
                    value?.status=Status.ERROR
                   // toast("Ooops: Something else went wrong")
                }


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