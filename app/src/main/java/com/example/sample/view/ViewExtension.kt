package com.example.sample.view
import android.view.View
import androidx.annotation.IdRes

fun<T: View> View.findViewById(@IdRes id: Int, config: T.() -> Unit): T {
    return findViewById<T>(id).apply {
        config()
    }
}