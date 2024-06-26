package com.jk.transaction_database.transaction.preview

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.OperationDatabaseEntity
import com.jk.transaction_database.transaction.TransactionMoneyDatabaseEntity
import com.jk.transaction_database.transaction.relations.MoneyRelation


data class OperationPreviewEntity(
    @Embedded
    val operation: OperationDatabaseEntity,

    @Relation(entity = TransactionMoneyDatabaseEntity::class,parentColumn = "money_id", entityColumn = "id")
    val moneyRelation: MoneyRelation
) {
}