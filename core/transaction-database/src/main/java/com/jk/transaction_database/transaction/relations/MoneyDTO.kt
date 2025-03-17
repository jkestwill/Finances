package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.entity.CurrencyEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity

data class MoneyDTO(
    @Embedded("money_")
    val moneyEntity:MoneyEntity,
    @Embedded("currency_")
    val currency:CurrencyEntity
)