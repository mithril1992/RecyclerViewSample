package com.example.sample.ui.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.view.DefaultItemTouchHelperCallback
import com.example.sample.view.findViewById

class AnimationListFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    private lateinit var viewModel: AnimationListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.animation_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.animation_list_recycler_view) {
            layoutManager = LinearLayoutManager(context)
            ItemTouchHelper(DefaultItemTouchHelperCallback()).attachToRecyclerView(this)

            val drawable = context.getDrawable(R.drawable.divider) ?: return@findViewById
            val decorater = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            decorater.setDrawable(drawable)
            addItemDecoration(decorater)
        }

        progressBar = view.findViewById(R.id.animation_list_progress_circular)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchDataAsync()
    }

    fun bindViewModel() {
        viewModel = ViewModelProviders.of(this).get(AnimationListViewModel::class.java)
        recyclerView.adapter = viewModel.adapter
    }
}