package com.example.currencyexchangeapi

import java.time.LocalDateTime

interface ExchangeServiceFactory {
    fun create(exchangeService: ExchangeRateServices): ExchangeRateService
}


sealed class ExchangeRateServices(val baseUrl: String, val countryTag: String) {
    class NBRB() : ExchangeRateServices(baseUrl = BuildConfig.NBRB_API_URL, countryTag = "BY")
    class DefaultRU(baseUrl: String) : ExchangeRateServices(baseUrl, countryTag = "RU")

    companion object {
        fun getByName(serviceName: String): ExchangeRateServices {
            return when (serviceName) {
                "NBRB" -> NBRB()
                else -> throw IllegalArgumentException("No such exchange service: $serviceName")
            }
        }
    }
}

interface ExchangeRateService {
    suspend fun exchange(exchangeRateRequestParams: ExchangeRateRequestParams): ExchangeRateApiRequest?
}

data class ExchangeRateRequestParams(
    val currencyIn: String,
    val currencyOut: String,
    val date: LocalDateTime
)

// change database
// isCurrencyInOnly - только одна валюта обмен которой можно посмотреть
// например у бнрб только белруб по отношениям к другим валютам
data class ExchangeRateApiRequest(
    val isCurrencyInOnly: Boolean? = null,
    val currencyIn: String,
    val currencyOut: String,
    val scale: Int,
    val rate: Double,
    val date: LocalDateTime,
    val bankName: String?
)

interface ExchangeRateMapResponse<I> : MapResponse<I, ExchangeRateApiRequest> {
    override fun map(): ExchangeRateApiRequest
}

interface MapResponse<I, O> {
    fun map(): O
}