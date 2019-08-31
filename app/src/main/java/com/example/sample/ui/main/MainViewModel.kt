package com.example.sample.ui.main

import androidx.lifecycle.ViewModel
import com.example.sample.model.rate.CurrencyPair
import com.example.sample.model.rate.ZaifRateDAO
import com.example.sample.view.BindingListViewAdapter
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    private val controller = MainListController<CurrencyPair>()
    private val source = MainListDataSource(emptyList<CurrencyPair>().toMutableList())
    val adapter = BindingListViewAdapter(controller, source)
    val zaifDao = ZaifRateDAO()
    var loading = false
        set(value) {
            field = value
            onLoadingChanged(value)
        }

    var onLoadingChanged: (Boolean)->Unit = { _ -> Unit }

    suspend fun fetchDataAsync() {
        supervisorScope {
            launch(Dispatchers.Main) {
                loading = true
            }
        }
        val currencies = zaifDao.fetchCurrencies().await()
        supervisorScope {
            launch(Dispatchers.Main) {
                source.updateDataSet(currencies)
                loading = false
            }
        }
    }
}
