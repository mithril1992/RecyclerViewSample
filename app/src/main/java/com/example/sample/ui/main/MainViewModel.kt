package com.example.sample.ui.main

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.sample.view.*

class MainViewModel : ViewModel() {
    companion object {
        fun createAdapter(): MovalbeBindingListAdapter<Int, MainListViewHolder> {
            val controller = MainListController()
            val source = MainListDataSource((0..9).toMutableList())

            controller.cellEventListener = listener@{
                val event = it as? MainListCellEvent ?: return@listener
                val vh = it.sender as? MainListViewHolder ?: return@listener
                if(event.status) {
                    vh.textView.setTextColor(Color.RED)
                } else {
                    vh.textView.setTextColor(Color.BLACK)
                }
            }

            controller.moveItemEventListener = listener@{ from, to ->
                source.moveItem(from, to)
            }

            return MovalbeBindingListAdapter(controller, source)
        }
    }

    val adapter = createAdapter()
}
