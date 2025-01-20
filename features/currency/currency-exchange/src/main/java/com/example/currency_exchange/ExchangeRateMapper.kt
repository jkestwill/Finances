package com.example.currency_exchange


import com.jk.money_common_data.ExchangeRate
import com.jk.money_common_ui.ExchangeRateUI

fun ExchangeRate.toExchangeRateUI(): ExchangeRateUI {
    return ExchangeRateUI(currencyIn, currencyOut, scale, rate, date,bank)
}