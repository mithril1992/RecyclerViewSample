package com.example.sample.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BindingListViewAdapter<T, VH : BindingListViewHolder<T>>(
    val controller: BindingListController<T, VH>,
    val dataSource: BindingListDataSource<T>
) : RecyclerView.Adapter<VH>() {
    init {
        dataSource.listener = createDataSourceEventListener()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewHolder = controller.createViewHolder(parent, viewType)
        viewHolder.cellEventListener = controller::onCellEvent
        return viewHolder
    }

    override fun getItemViewType(position: Int) = controller.viewTypeOf(dataSource[position])

    override fun getItemCount() = dataSource.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindViewModel(dataSource[position])
    }
}

abstract internal class DataSourceEventListener {
    abstract fun onDataSetChanged()
    abstract fun onItemChanged(position: Int)
    abstract fun onItemInserted(position: Int)
    abstract fun onItemMoved(from: Int, to: Int)
    abstract fun onItemRemoved(position: Int)
    abstract fun onItemRangeChanged(begin: Int, end: Int)
    abstract fun onItemRangeInserted(begin: Int, end: Int)
    abstract fun onItemRangeRemoved(begin: Int, end: Int)
}

internal fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.createDataSourceEventListener() =
    object : DataSourceEventListener() {
        override fun onDataSetChanged() = notifyDataSetChanged()
        override fun onItemChanged(position: Int) = notifyItemChanged(position)
        override fun onItemInserted(position: Int) = notifyItemInserted(position)
        override fun onItemMoved(from: Int, to: Int) = notifyItemMoved(from, to)
        override fun onItemRemoved(position: Int) = notifyItemRemoved(position)
        override fun onItemRangeChanged(begin: Int, end: Int) = notifyItemRangeChanged(begin, end)
        override fun onItemRangeInserted(begin: Int, end: Int) = notifyItemRangeInserted(begin, end)
        override fun onItemRangeRemoved(begin: Int, end: Int) = notifyItemRangeRemoved(begin, end)
    }

open class CellEvent(val sender: RecyclerView.ViewHolder)

abstract class BindingListViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var cellEventListener: ((CellEvent) -> Unit)? = null

    abstract fun bindViewModel(cellModel: T)

    protected open fun notifyCellEvent(cellEvent: CellEvent) {
        cellEventListener?.invoke(cellEvent)
    }
}

// View関係のハンドラを実装するクラス
interface BindingListController<T, VH : BindingListViewHolder<T>> {
    fun onCellEvent(event: CellEvent)
    fun createViewHolder(parent: ViewGroup, viewType: Int): VH
    fun viewTypeOf(cellModel: T): Int = 0
}

// Model関係のハンドラを実装するクラス
abstract class BindingListDataSource<T> {
    internal var listener: DataSourceEventListener? = null

    abstract val size: Int
    abstract operator fun get(position: Int): T

    fun onDataSetChanged() = listener?.onDataSetChanged()
    fun onItemChanged(position: Int) = listener?.onItemChanged(position)
    fun onItemInserted(position: Int) = listener?.onItemInserted(position)
    fun onItemMoved(from: Int, to: Int) = listener?.onItemMoved(from, to)
    fun onItemRemoved(position: Int) = listener?.onItemRemoved(position)
    fun onItemRangeChanged(begin: Int, end: Int) = listener?.onItemRangeChanged(begin, end)
    fun onItemRangeInserted(begin: Int, end: Int) = listener?.onItemRangeInserted(begin, end)
    fun onItemRangeRemoved(begin: Int, end: Int) = listener?.onItemRangeRemoved(begin, end)
}