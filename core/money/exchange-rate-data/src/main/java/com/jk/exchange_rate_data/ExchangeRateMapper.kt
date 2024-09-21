package com.jk.exchange_rate_data

import com.example.currencyexchangeapi.Address
import com.example.currencyexchangeapi.Bank
import com.example.currencyexchangeapi.ExchangeRateApiRequest
import com.jk.common_data.sha256
import com.jk.transaction_database.transaction.AddressEntity
import com.jk.transaction_database.transaction.BankEntity
import com.jk.transaction_database.transaction.ExchangeRateEntity
import com.jk.transaction_database.transaction.relations.BankRelation
import com.jk.transaction_database.transaction.relations.ExchangeRateRelation

fun ExchangeRateRelation.toExchangeRate(): ExchangeRate {
    return ExchangeRate(
        currencyIn = currencyFrom.name,
        currencyOut = currencyTo.name,
        scale = exchangeRateEntity.scale,
        rate = exchangeRateEntity.rate,
        date = exchangeRateEntity.date,
        bank = bankRelation?.toBank()
    )
}

fun BankRelation.toBank(): Bank {
    return Bank(id="${bankEntity.name}${bankEntity.url}${bankEntity.abbreviation}".sha256(), name=bankEntity.name, url = bankEntity.url,abbreviation=bankEntity.abbreviation, imageUrl = bankEntity.imageUrl, address =addressEntity.toAddress() )
}

fun AddressEntity.toAddress(): Address {
    return Address(
        id=id, long = long,lat = lat,address=address
    )
}


fun ExchangeRateApiRequest.toExchangeRate(bank: Bank?): ExchangeRate {
    return ExchangeRate(
        currencyIn=currencyIn,
        currencyOut=currencyOut,
        scale=scale,
        rate=rate,
        date=date,
        bank = bank
    )
}

