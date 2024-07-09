package com.jk.currency

import com.jk.money_data.Currency
import com.jk.money_data.Money

fun Money.toUI(): MoneyUI {
    return MoneyUI(id=id,amount=amount, currency = currency.toUI())
}

fun Currency.toUI(): CurrencyUI {
    return CurrencyUI(id=id,name=name)
}