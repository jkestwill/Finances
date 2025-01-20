package com.jk.money_common_ui

import com.jk.money_common_data.Bank
import java.time.LocalDateTime

data class ExchangeRateUI(
    val currencyIn: String,
    val currencyOut: String,
    val scale: Int,
    val rate: Double,
    val date: LocalDateTime,
    val bank: Bank?,
)