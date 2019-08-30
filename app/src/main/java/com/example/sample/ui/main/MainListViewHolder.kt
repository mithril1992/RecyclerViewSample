package com.example.sample.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.example.sample.R
import com.example.sample.view.*

class MainListViewHolder<T>(itemView: View) : BindingListViewHolder<T>(itemView) {
    companion object {
        fun<T> createWith(parent: ViewGroup): MainListViewHolder<T> {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.main_list_cell, parent, false)
            return MainListViewHolder(itemView)
        }
    }

    val textView: TextView = itemView.findViewById(R.id.main_list_cell_text)

    override fun bindViewModel(cellModel: T) {
        textView.text = cellModel.toString()
    }
}

class MainListController<T> : BindingListController<T, MainListViewHolder<T>> {
    override fun onCellEvent(event: CellEvent) = Unit
    override fun createViewHolder(parent: ViewGroup, viewType: Int) = MainListViewHolder.createWith<T>(parent)
}

class MainListDataSource<T>(private var list: MutableList<T>) : BindingListDataSource<T>() {
    override val size
        get() = list.size
    override fun get(position: Int) = list.get(position)

    fun updateDataset(newList: List<T>) {
        list = newList.toMutableList()
        onDataSetChanged()
    }
}