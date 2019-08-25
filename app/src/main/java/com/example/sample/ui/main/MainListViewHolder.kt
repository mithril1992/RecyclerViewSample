package com.example.sample.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.example.sample.R
import com.example.sample.view.*


class MainListCellEvent(sender: MainListViewHolder, val status: Boolean) : CellEvent(sender)

class MainListViewHolder(itemView: View) : BindingListViewHolder<Int>(itemView) {
    companion object {
        fun createWith(parent: ViewGroup): MainListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.main_list_cell, parent, false)
            return MainListViewHolder(itemView)
        }
    }

    var model: Int? = null
    val textView: TextView
    val switch: Switch

    init {
        textView = itemView.findViewById(R.id.main_list_cell_text)
        switch = itemView.findViewById(R.id.main_list_cell_switch)
        switch.setOnCheckedChangeListener { button, status ->
            model?.also {
                notifyCellEvent(MainListCellEvent(this, status))
            }
        }
    }

    override fun bindViewModel(cellModel: Int) {
        model = cellModel
        if (cellModel < 0) {
            textView.text = (-cellModel).toString()
            textView.setTextColor(Color.RED)
        } else {
            textView.text = cellModel.toString()
            textView.setTextColor(Color.BLACK)
        }
    }
}

class MainListController : MovableBindingListController<Int, MainListViewHolder> {
    var cellEventListener: ((CellEvent) -> Unit) = { _ -> Unit }
    var moveItemEventListener: (Int, Int) -> Unit = { _, _ -> Unit }
    override fun onCellEvent(event: CellEvent) = cellEventListener(event)
    override fun createViewHolder(parent: ViewGroup, viewType: Int) =
        MainListViewHolder.createWith(parent)
    override fun omMoved(from: Int, to: Int) = moveItemEventListener.invoke(from, to)
}

class MainListDataSource(private val list: MutableList<Int>) : BindingListDataSource<Int>() {
    override val size = list.size
    override fun get(position: Int) = list.get(position)
    fun moveItem(from: Int, to: Int) {
        val item = list.removeAt(from)
        list.add(to, item)
        onItemMoved(from, to)
    }
}