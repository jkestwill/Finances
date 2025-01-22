package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.CurrencyEntity
import com.jk.transaction_database.transaction.MoneyEntity

data class MoneyRelation(
    @Embedded
    val money: MoneyEntity,
    @Relation(
        parentColumn = "currency_id",
        entityColumn = "id"
    )
    val currency: CurrencyEntity
) {
}