package com.example.sample.model.rate

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference

object CurrencyPairList: TypeReference<List<CurrencyPair>>()

@JsonIgnoreProperties(ignoreUnknown=true)
class CurrencyPair(
    @JsonProperty("name") val name: String,
    @JsonProperty("currency_pair") val signature: String
) {
    override fun toString() = "$name($signature)"
}