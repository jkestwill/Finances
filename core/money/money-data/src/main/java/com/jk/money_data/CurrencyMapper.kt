package com.jk.money_data

import com.jk.money_common_data.Currency
import com.jk.transaction_database.transaction.entity.CurrencyEntity

fun CurrencyEntity.toCurrency(): Currency {
    return Currency(id,name)
}