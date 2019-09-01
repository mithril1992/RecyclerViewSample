package com.example.sample.ui.animation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.sample.R
import com.example.sample.model.check.Check
import com.example.sample.view.*
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.ionicons.Ionicons
import com.mikepenz.iconics.utils.toIconicsColor
import com.mikepenz.iconics.utils.toIconicsSizeDp


class AnimationListViewHolder(itemView: View) : BindingListViewHolder<Check>(itemView) {
    companion object {
        fun createWith(parent: ViewGroup): AnimationListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.animation_list_cell, parent, false)

            return AnimationListViewHolder(itemView)
        }
    }

    class OnTouchSwitchEvent(sendar: AnimationListViewHolder, val newState: Boolean) :
        CellEvent(sendar)

    class OnTouchDeleteButtonEvent(sendar: AnimationListViewHolder) : CellEvent(sendar)

    private lateinit var model: Check;
    private val text = itemView.findViewById<TextView>(R.id.animation_list_cell_text)
    private val switch = itemView.findViewById<ImageView>(R.id.animation_list_cell_icon) {
        val iconDrawable = IconicsDrawable(itemView.context)
            .icon(Ionicons.Icon.ion_android_remove_circle)
            .color(Color.RED.toIconicsColor())
            .size(18.toIconicsSizeDp())
        setImageDrawable(iconDrawable)
        setOnTouchListener { v, event ->
            if (event.action != MotionEvent.ACTION_DOWN) {
                return@setOnTouchListener true
            }

            val currentState = model.switchState
            model.switchState = !currentState
//            notifyCellEvent(OnTouchSwitchEvent(this@AnimationListViewHolder, !currentState))
            if (!currentState) {
                switchOn(true)
            } else {
                switchOff(true)
            }

            return@setOnTouchListener true
        }
    }

    private val handle = itemView.findViewById<ImageView>(R.id.animation_list_cell_drag_handle) {
        val iconDrawable = IconicsDrawable(itemView.context)
            .icon(Ionicons.Icon.ion_android_menu)
            .color(Color.GRAY.toIconicsColor())
            .size(18.toIconicsSizeDp())
        setImageDrawable(iconDrawable)
    }

    private val deleteButton = itemView.findViewById<Button>(R.id.animation_list_cell_delete_button) {
        setOnTouchListener { v, event ->
            if (event.action != MotionEvent.ACTION_DOWN) {
                return@setOnTouchListener true
            }

            notifyCellEvent(OnTouchDeleteButtonEvent(this@AnimationListViewHolder))
            return@setOnTouchListener true
        }
    }

    private val switchOnRotationAnimation = RotateAnimation(
        0f, 90f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    ).also {
        it.duration = 200
        it.fillAfter = true
    }

    private val switchOffRotateAnimation = RotateAnimation(
        90f, 0f,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    ).also {
        it.duration = 200
        it.fillAfter = true
    }

    private val switchOnAppearAnimation = AlphaAnimation(0f, 1f).also {
        it.duration = 200
        it.setAnimationListener(appearAnimationListener)
    }

    private val switchOffDisappearAnimation = AlphaAnimation(1f, 0f).also {
        it.duration = 200
        it.setAnimationListener(disappearAnimationListener)
    }

    private val appearAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) = Unit
        override fun onAnimationStart(animation: Animation?) = Unit
        override fun onAnimationEnd(animation: Animation?) {
            switch.alpha = 1f
        }
    }

    private val disappearAnimationListener = object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) = Unit
        override fun onAnimationStart(animation: Animation?) = Unit
        override fun onAnimationEnd(animation: Animation?) {
            deleteButton.alpha = 0f
            deleteButton.isVisible = false
        }
    }

    override fun bindViewModel(cellModel: Check, recycled: Boolean) {
        model = cellModel
        text.text = cellModel.text
        switch.rotation = 0f
        if (cellModel.switchState) {
            switchOn(!recycled)
        } else {
            switchOff(!recycled)
        }
    }

    fun switchOn(animation: Boolean) {
        deleteButton.isVisible = true

        if (!animation) {
            switch.rotation = 90f
            deleteButton.alpha = 1f
            return
        }

        switch.startAnimation(switchOnRotationAnimation)
        deleteButton.startAnimation(switchOnAppearAnimation)
    }

    fun switchOff(animation: Boolean) {
        if (!animation) {
            switch.rotation = 0f
            deleteButton.isVisible = false
            deleteButton.alpha = 0f
            return
        }

        switch.startAnimation(switchOffRotateAnimation)
        deleteButton.startAnimation(switchOffDisappearAnimation)
    }
}

class AnimationListController(val handler: (CellEvent) -> Unit) :
    BindingListController<Check, AnimationListViewHolder> {
    override fun onCellEvent(event: CellEvent) = handler(event)
    override fun createViewHolder(parent: ViewGroup, viewType: Int): AnimationListViewHolder {
        return AnimationListViewHolder.createWith(parent)
    }
}

class AnimationListDataSource(private var list: MutableList<Check>) :
    BindingListDataSource<Check>() {
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

    fun deleteItem(index: Int): Check {
        val item = list.removeAt(index)
        onItemRemoved(index)
        return item
    }
}