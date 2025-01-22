package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.BankEntity
import com.jk.transaction_database.transaction.ExchangeRateEntity
import com.jk.transaction_database.transaction.CurrencyEntity

data class ExchangeRateRelation(
    @Embedded
    val exchangeRateEntity: ExchangeRateEntity,

    @Relation(parentColumn = "currency_from_id", entityColumn = "id")
    val currencyFrom: CurrencyEntity,

    @Relation(parentColumn = "currency_to_id", entityColumn = "id")
    val currencyTo:CurrencyEntity,

    @Relation(parentColumn = "bank_id", entityColumn = "id", entity = BankEntity::class)
    val bankRelation: BankRelation?
) {

}