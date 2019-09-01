package com.example.sample.ui.currencies

import androidx.lifecycle.ViewModel
import com.example.sample.model.rate.CurrencyPair
import com.example.sample.model.rate.ZaifRateDAO
import com.example.sample.view.BindingListViewAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class CurrenciesListViewModel : ViewModel() {
    private val controller = CurrenciesListController<CurrencyPair>()
    private val source =
        CurrenciesListDataSource(emptyList<CurrencyPair>().toMutableList())
    val adapter = BindingListViewAdapter(controller, source)
    val zaifDao = ZaifRateDAO()
    var loading = false
        set(value) {
            field = value
            onLoadingChanged(value)
        }

    var onLoadingChanged: (Boolean) -> Unit = { _ -> Unit }

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