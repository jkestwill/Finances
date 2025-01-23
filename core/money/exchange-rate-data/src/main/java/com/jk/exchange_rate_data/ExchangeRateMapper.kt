package com.jk.exchange_rate_data

//import com.example.currencyexchangeapi.ExchangeRateApiRequest
//import com.jk.common_data.sha256
//import com.jk.money_common_data.Bank
//import com.jk.transaction_database.transaction.AddressEntity

//fun ExchangeRateRelation.toExchangeRate(): com.jk.money_common_data.ExchangeRate {
//    return com.jk.money_common_data.ExchangeRate(
//        currencyIn = currencyFrom.name,
//        currencyOut = currencyTo.name,
//        scale = exchangeRateEntity.scale,
//        rate = exchangeRateEntity.rate,
//        date = exchangeRateEntity.date,
//        bank = bankRelation?.toBank()
//    )
//}
//
//fun BankRelation.toBank(): Bank {
//    return Bank(id="${bankEntity.name}${bankEntity.url}${bankEntity.abbreviation}".sha256(), name=bankEntity.name, url = bankEntity.url,abbreviation=bankEntity.abbreviation, imageUrl = bankEntity.imageUrl, address =addressEntity.toAddress() )
//}
//
//fun AddressEntity.toAddress(): com.jk.money_common_data.Address {
//    return com.jk.money_common_data.Address(
//        id = id, long = long, lat = lat, address = address
//    )
//}
//
//
//fun ExchangeRateApiRequest.toExchangeRate(bank: Bank?): com.jk.money_common_data.ExchangeRate {
//    return com.jk.money_common_data.ExchangeRate(
//        currencyIn = currencyIn,
//        currencyOut = currencyOut,
//        scale = scale,
//        rate = rate,
//        date = date,
//        bank = bank
//    )
//}

