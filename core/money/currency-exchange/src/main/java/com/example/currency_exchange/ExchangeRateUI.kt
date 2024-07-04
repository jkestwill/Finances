package com.example.currency_exchange

import java.time.LocalDateTime

data class ExchangeRateUI(
    val currencyIn: String,
    val currencyOut: String,
    val scale: Int,
    val rate: Double,
    val date: LocalDateTime

)