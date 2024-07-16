package com.jk.money_common_ui


import com.jk.money_common_data.Currency
import com.jk.money_common_data.Money


fun Money.toUI(): MoneyUI {
    return MoneyUI(id = id, amount = amount, currency = currency.toUI())
}

fun Currency.toUI(): CurrencyUI {
    return CurrencyUI(id = id, name = name)
}