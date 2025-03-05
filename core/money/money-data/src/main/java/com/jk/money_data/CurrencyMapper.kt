package com.jk.money_data

import com.jk.money_common_data.Currency
import com.jk.transaction_database.transaction.entity.CurrencyEntity
import org.mapstruct.Mapper

@Mapper
interface CurrencyMapper{

    fun toEntity(currency: Currency):CurrencyEntity

    fun toCurrency(entity: CurrencyEntity):Currency

}