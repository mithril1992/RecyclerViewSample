package com.example.sample.ui.main

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.sample.model.rate.CurrencyPair
import com.example.sample.model.rate.ZaifRateDAO
import com.example.sample.view.BindingListViewAdapter
import com.example.sample.view.MovalbeBindingListAdapter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

class MainViewModel : ViewModel() {
    private val controller = MainListController<CurrencyPair>()
    private val source = MainListDataSource(emptyList<CurrencyPair>().toMutableList())
    val adapter = BindingListViewAdapter(controller, source)
    val zaifDao = ZaifRateDAO()

    suspend fun fetchDataAsync() {
        val currencies = zaifDao.fetchCurrencies().await()
        supervisorScope {
            launch(Dispatchers.Main) {
                source.updateDataset(currencies)
            }
        }
    }
}
