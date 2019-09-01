package com.example.sample.ui.animation

import androidx.lifecycle.ViewModel
import com.example.sample.model.check.Check
import com.example.sample.view.BindingListViewAdapter

class AnimationListViewModel : ViewModel() {
    private val controller = AnimationListController {
        if(it is AnimationListViewHolder.OnTouchSwitchEvent) {
            val index = it.sender.adapterPosition
            val item = source[index]
//            source.updateItem(Check(item.text, it.newState), index)
            return@AnimationListController
        }
        if(it is AnimationListViewHolder.OnTouchDeleteButtonEvent) {
            val index = it.sender.adapterPosition
            source.deleteItem(index)
            return@AnimationListController
        }
    }
    private val source = AnimationListDataSource(mutableListOf())

    val adapter = BindingListViewAdapter(controller, source)

    fun fetchDataAsync() {
        source.updateDataSet((0..40).map { Check(it.toString(), false) }.toMutableList())
    }
}