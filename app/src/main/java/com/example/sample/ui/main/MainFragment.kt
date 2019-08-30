package com.example.sample.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.view.DefaultItemTouchHelperCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.main_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        ItemTouchHelper(DefaultItemTouchHelperCallback()).attachToRecyclerView(recyclerView)

        val thisContext = context ?: return
        val drawable = thisContext.getDrawable(R.drawable.divider) ?: return
        val decorater = DividerItemDecoration(thisContext, LinearLayoutManager.VERTICAL)
        decorater.setDrawable(drawable)
        recyclerView.addItemDecoration(decorater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            viewModel.fetchDataAsync()
        }
    }

    fun bindViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        recyclerView.adapter = viewModel.adapter
    }
}
