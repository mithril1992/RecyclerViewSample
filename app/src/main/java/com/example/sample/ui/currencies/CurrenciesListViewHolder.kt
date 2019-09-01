package com.example.sample.ui.currencies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sample.R
import com.example.sample.view.*

class CurrenciesListViewHolder<T>(itemView: View) : BindingListViewHolder<T>(itemView) {
    companion object {
        fun<T> createWith(parent: ViewGroup): CurrenciesListViewHolder<T> {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.main_list_cell, parent, false)
            return CurrenciesListViewHolder(itemView)
        }
    }

    val textView: TextView = itemView.findViewById(R.id.main_list_cell_text)

    override fun bindViewModel(cellModel: T, recycled: Boolean) {
        textView.text = cellModel.toString()
    }
}

class CurrenciesListController<T> : BindingListController<T, CurrenciesListViewHolder<T>> {
    override fun onCellEvent(event: CellEvent) = Unit
    override fun createViewHolder(parent: ViewGroup, viewType: Int) =
        CurrenciesListViewHolder.createWith<T>(parent)
}

class CurrenciesListDataSource<T>(private var list: MutableList<T>) : BindingListDataSource<T>() {
    override val size
        get() = list.size
    override fun get(position: Int) = list.get(position)

    fun updateDataSet(newList: List<T>) {
        list = newList.toMutableList()
        onDataSetChanged()
    }
}