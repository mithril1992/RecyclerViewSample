package com.example.sample.ui.currencies

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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrenciesListFragment : Fragment() {
    companion object {
        fun newInstance() = CurrenciesListFragment()
    }

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar

    private lateinit var viewModel: CurrenciesListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.currencies_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.currencies_list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        ItemTouchHelper(DefaultItemTouchHelperCallback()).attachToRecyclerView(recyclerView)

        val thisContext = context ?: return
        val drawable = thisContext.getDrawable(R.drawable.divider) ?: return
        val decorater = DividerItemDecoration(thisContext, LinearLayoutManager.VERTICAL)
        decorater.setDrawable(drawable)
        recyclerView.addItemDecoration(decorater)

        progressBar = view.findViewById(R.id.currencies_list_progress_circular)

        activity?.actionBar?.also {
            it.title = "Currencies"
        }
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
        viewModel = ViewModelProviders.of(this).get(CurrenciesListViewModel::class.java)
        recyclerView.adapter = viewModel.adapter
        viewModel.onLoadingChanged = { loading ->
            progressBar.visibility = if(loading) ProgressBar.VISIBLE else ProgressBar.INVISIBLE
        }
    }
}