package com.example.sample.ui.animation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.example.sample.R
import com.example.sample.model.check.Check
import com.example.sample.view.*
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.animation.IconicsAnimatedDrawable
import com.mikepenz.iconics.typeface.library.ionicons.Ionicons
import com.mikepenz.iconics.utils.toIconicsColor
import com.mikepenz.iconics.utils.toIconicsSizeDp


class AnimationListViewHolder(itemView: View): BindingListViewHolder<Check>(itemView) {
    companion object {
        fun createWith(parent: ViewGroup): AnimationListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.animation_list_cell, parent, false)

            return AnimationListViewHolder(itemView)
        }
    }

    class OnTouchSwitchEvent(sendar: AnimationListViewHolder, val newState: Boolean) : CellEvent(sendar)

    lateinit var model: Check;
    val text = itemView.findViewById<TextView>(R.id.animation_list_cell_text)
    val switch = itemView.findViewById<ImageView>(R.id.animation_list_cell_icon) {
        val iconDrawable = IconicsDrawable(itemView.context)
            .icon(Ionicons.Icon.ion_android_remove_circle)
            .color(Color.RED.toIconicsColor())
            .size(18.toIconicsSizeDp())
        setImageDrawable(iconDrawable)
        setOnTouchListener { v, event ->
            if(event.action != MotionEvent.ACTION_DOWN) {
                return@setOnTouchListener true
            }

            val currentState = model.switchState
            notifyCellEvent(OnTouchSwitchEvent(this@AnimationListViewHolder, !currentState))

            return@setOnTouchListener true
        }
    }

    override fun bindViewModel(cellModel: Check, recycled: Boolean) {
        model = cellModel
        text.text = cellModel.text
        if(cellModel.switchState) {
            switchOn(!recycled)
        } else {
            switchOff(!recycled)
        }
    }

    fun switchOn(animation: Boolean) {
        if(!animation) {
            switch.rotation = 90f
            return
        }

        val rotAnimation = RotateAnimation(0f, 90f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        rotAnimation.duration = 200
        rotAnimation.fillAfter = true

        switch.startAnimation(rotAnimation)
    }

    fun switchOff(animation: Boolean) {
        if(!animation) {
            switch.rotation = 0f
            return
        }

        val rotAnimation = RotateAnimation(90f, 0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        rotAnimation.duration = 200
        rotAnimation.fillAfter = true

        switch.startAnimation(rotAnimation)
    }
}

class AnimationListController(val handler: (CellEvent)->Unit): BindingListController<Check, AnimationListViewHolder> {
    override fun onCellEvent(event: CellEvent) = handler(event)
    override fun createViewHolder(parent: ViewGroup, viewType: Int): AnimationListViewHolder {
        return AnimationListViewHolder.createWith(parent)
    }
}

class AnimationListDataSource(private var list: MutableList<Check>) : BindingListDataSource<Check>() {
    override val size
        get() = list.size
    override fun get(position: Int) = list.get(position)

    fun updateDataSet(newList: List<Check>) {
        list = newList.toMutableList()
        onDataSetChanged()
    }

    fun updateItem(data: Check, index: Int) {
        list[index] = data
        onItemChanged(index)
    }
}