package com.example.bioforumis.view

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bioforumis.R
import com.example.bioforumis.service.adapter.MainAdapter
import com.example.bioforumis.service.model.Apod
import com.example.bioforumis.service.utils.Status
import com.example.bioforumis.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity () {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView:RecyclerView
    private lateinit var progressBar:ProgressBar
    var fab:FloatingActionButton?=null
    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private var apod_list: ArrayList<Apod> ?=null
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
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }
    private fun setupObservers() {

        Log.e("errr","errror"+viewModel)
        viewModel.apodList.observe(this, Observer {
            it?.let { response ->
                if (response!= null) {
                    when (response.status) {
                        Status.SUCCESS -> {
                            recyclerView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            response.data?.let { apods -> retrieveList(apods) }
                        }
                        Status.ERROR -> {
                            recyclerView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE

                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            progressBar.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        }
                        Status.UNKNOWN ->{
                            Log.e("errr","unknown")
                        }
                    }
                }
            }
        })

        viewModel.getApods1()
    }
    private fun setupObserverBydate(date:String) {
        viewModel.getApod(date).observe(this, Observer {
            it?.let { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        response.data?.let { apod-> retrieveList(apod) }!!
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }
    private fun retrieveList(apods: List<Apod>) {
        apod_list!!.addAll(apods)
      Log.e("size",apods.size.toString())
    /* for (apos in apods){
         Log.e("date",apos.date)
     }*/
       adapter.addApod(apods)

    }

    private fun retrieveList(apod: Apod) {
        /*     Log.e("size",apods.size.toString())
          for (apos in apods){
              Log.e("date",apos.date)
          }*/
        apod_list!!.add(apod)
        adapter.addApod(apod_list!!)

    }
}
