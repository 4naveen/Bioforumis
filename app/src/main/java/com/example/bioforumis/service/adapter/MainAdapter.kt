package com.example.bioforumis.service.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bioforumis.R
import com.example.bioforumis.service.model.Apod
import com.example.bioforumis.view.DiffUtilCallback
import kotlinx.android.synthetic.main.row_item.view.title
import kotlinx.android.synthetic.main.row_item.view.date
import kotlinx.android.synthetic.main.row_item.view.image

class MainAdapter(private val apods: ArrayList<Apod>) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(apod: Apod) {
            itemView.apply {
                title.text = apod.title
                date.text = apod.date
                Glide.with(image.context)
                    .load(apod.image_url)
                    .into(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false))

    override fun getItemCount(): Int = apods.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(apods[position])
    }

    fun addApod(newapods: List<Apod>) {

        val diffCallback = DiffUtilCallback(apods, newapods)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        apods.clear()
        apods.addAll(newapods)
        diffResult.dispatchUpdatesTo(this)

    }
}