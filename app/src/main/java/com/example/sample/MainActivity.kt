package com.example.sample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sample.ui.main.MainFragment
import com.mikepenz.iconics.utils.setIconicsFactory
import com.mikepenz.iconics.utils.wrapByIconics

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
