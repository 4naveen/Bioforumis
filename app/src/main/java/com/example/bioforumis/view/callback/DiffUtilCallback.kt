package com.example.bioforumis.view.callback

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.bioforumis.service.model.data.Apod

class DiffUtilCallback(private val oldList: List<Apod>, private val newList: List<Apod>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].date === newList.get(newItemPosition).date
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, value, name) = oldList[oldPosition]
        val (_, value1, name1) = newList[newPosition]

        return name == name1 && value == value1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}