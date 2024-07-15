package com.jk.money_common_ui

import java.time.LocalDateTime

data class ExchangeRateUI(
    val currencyIn: String,
    val currencyOut: String,
    val scale: Int,
    val rate: Double,
    val date: LocalDateTime

)