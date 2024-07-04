package com.example.currency_exchange

import com.jk.exchange_rate_data.ExchangeRate

fun ExchangeRate.toExchangeRateUI(): ExchangeRateUI {
    return ExchangeRateUI(currencyIn, currencyOut, scale, rate, date)
}