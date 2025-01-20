package com.jk.transaction.mapper

import com.jk.money_common_data.Currency
import com.jk.money_common_data.ExchangeRate
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.ExchangeRateUI


fun ExchangeRate.toUI(): ExchangeRateUI {
   return ExchangeRateUI(currencyIn=currencyIn,currencyOut=currencyOut,scale=scale,rate = rate,date = date,bank=bank)
}
fun ExchangeRateUI.toExchange(): ExchangeRate {
    return ExchangeRate(currencyIn = currencyIn,currencyOut=currencyOut,scale=scale,rate=rate,date = date,bank)
}
