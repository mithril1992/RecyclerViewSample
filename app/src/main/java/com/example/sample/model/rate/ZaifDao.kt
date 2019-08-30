package com.example.sample.model.rate

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import okhttp3.Request

class ZaifRateDAO {
    val mapper = ObjectMapper()
    val client = OkHttpClient()

    suspend fun fetchCurrencies(): Deferred<List<CurrencyPair>> = coroutineScope {
        async {
            val request = Request.Builder().url("https://api.zaif.jp/api/1/currency_pairs/all").build()
            val response = client.newCall(request).execute()

            return@async if(response.isSuccessful) {
                mapper.readValue<List<CurrencyPair>>(response.body()?.string(), CurrencyPairList)
            } else {
                emptyList()
            }
        }
    }
}