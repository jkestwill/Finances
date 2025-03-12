package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.entity.CurrencyEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity

data class MoneyDTO(
    @Embedded
    val moneyEntity:MoneyEntity,
    @Relation(parentColumn = "currency_id", entityColumn =  "id")
    val currency:CurrencyEntity
)