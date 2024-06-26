package com.jk.exchange_rate_data

import com.example.currencyexchangeapi.model.NBRB
import com.jk.transaction_database.transaction.relations.ExchangeRateRelation

fun ExchangeRateRelation.toExchangeRate(): ExchangeRate {
    return ExchangeRate(
        currencyIn = currencyFrom.name,
        currencyOut = currencyTo.name,
        scale = this.exchangeRateEntity.scale,
        rate = this.exchangeRateEntity.rate,
        date = this.exchangeRateEntity.date
    )
}

fun NBRB.toExchangeRate(): ExchangeRate {
    return ExchangeRate(
        currencyIn = this.currencyIn,
        currencyOut = this.currencyAbbreviation,
        scale = scale,
        rate = rate,
        date = date
    )
}

