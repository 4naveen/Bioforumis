package com.example.bioforumis.view.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bioforumis.R
import com.example.bioforumis.service.data.remote.Apod
import com.example.bioforumis.service.data.remote.Response
import com.example.bioforumis.view.adapter.MainAdapter
import com.example.bioforumis.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity () {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView:RecyclerView
    private lateinit var progressBar:ProgressBar
    var fab:FloatingActionButton?=null
    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private var apod_list: ArrayList<Apod> ?= ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionbar=supportActionBar
        if (actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setTitle(R.string.app_name)
        }
        //setSupportActionBar(findViewById(R.id.toolbar))
        setupViewModel()
        setupUI()
        setupObservers()
        fab!!.setOnClickListener { view ->

            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            val datePickerDialog = DatePickerDialog(
                this@MainActivity,
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar[year, monthOfYear] = dayOfMonth
                    val format = SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH)
                    val strDate: String = format.format(calendar.time)
                    setupObserverBydate(strDate)
                    Log.e("sel_date", strDate)
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show();
        }
    }


    private fun setupViewModel() {

        viewModel = ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)

    }

    private fun setupUI() {
        recyclerView=findViewById(R.id.recyclerview)
        progressBar=findViewById(R.id.progressBar)
        fab=findViewById(R.id.fab)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }
    private fun setupObservers() {

        viewModel.apodList.observe(this, Observer {
            it?.let { response ->
                when (response.status) {
                    Response.Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        response.data?.let { apods -> saveList(apods) }
                    }
                    Response.Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE

                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Response.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                    Response.Status.UNKNOWN ->{
                        Log.e("errr","unknown")
                    }
                }
            }
        })
        viewModel.getApods()
    }

    private fun setupObserverBydate(date:String) {
        viewModel.apod.observe(this, Observer {
            it?.let { response ->
                when (response.status) {
                    Response.Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        response.data?.let { apods -> saveList(apods) }
                    }
                    Response.Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE

                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Response.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                    Response.Status.UNKNOWN ->{
                        Log.e("errr","unknown")
                    }
                }
            }
        })
        viewModel.getApod(date)
    }
    private fun saveList(apods: List<Apod>) {
        apod_list?.clear()
        apod_list?.addAll(apods)
       adapter.addApod(apod_list as ArrayList)

    }

    private fun saveList(apod: Apod) {
             Log.e("size",apod.toString())
        apod_list!!.add(apod)
        adapter.addApod(apod_list!!)

    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.deleteDisposable()
    }

}
