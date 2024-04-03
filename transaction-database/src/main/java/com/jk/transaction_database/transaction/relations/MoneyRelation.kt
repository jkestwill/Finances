package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.TransactionCurrencyDatabaseEntity
import com.jk.transaction_database.transaction.TransactionMoneyDatabaseEntity

data class MoneyRelation(
    @Embedded
    val money: TransactionMoneyDatabaseEntity,
    @Relation(
        parentColumn = "currency_id",
        entityColumn = "id"
    )
    val currency: TransactionCurrencyDatabaseEntity
) {
}