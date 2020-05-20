package com.example.bioforumis.view

import android.app.Application
import android.content.Context

class MyApplication : Application(){

    companion object{
        fun getAppContext():Context{
            return MyApplication();
        }
    }
}