package com.example.bioforumis.service.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class GeneralService {

   companion object {
       fun isOnline(context: Context): Boolean {
           val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
           val netInfo = cm.activeNetworkInfo
           return netInfo != null && netInfo.isConnected
       }

       fun showToast(mContext: Context?, message: String?) {
           try {
               Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
           } catch (e: Exception) {
               e.printStackTrace()
           }
       }

       fun getEnddate(): String {
           try {
               val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
               formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"))
               return formatter.format(Date())
               //Log.e("enddate", formatter.format(Date()))

           } catch (e: ParseException) {
               e.printStackTrace()
           }
           return ""
       }
       fun getStartDate():String{
           var df = SimpleDateFormat("YYYY-MM-dd")
           val c = Calendar.getInstance()
           c.add(Calendar.DAY_OF_YEAR, -30)
           val startDate = df.format(c.time)
           Log.e("startDate", startDate)

           return startDate
       }
   }
}