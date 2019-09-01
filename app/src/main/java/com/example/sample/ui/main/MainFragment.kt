package com.example.sample.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.sample.R
import com.example.sample.view.findViewById

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.main_to_currencies_button) {
            setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.from_main_to_currencies)
            }
        }

        view.findViewById<Button>(R.id.main_to_animation_button) {
            setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.from_main_to_animation_list)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    fun bindViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
}
