package com.jk.transaction_database.transaction.preview

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionMoneyDatabaseEntity
import com.jk.transaction_database.transaction.relations.MoneyRelation


data class OperationPreviewEntity(
    @Embedded
    val operation: OperationEntity,

    @Relation(entity = TransactionMoneyDatabaseEntity::class,parentColumn = "money_id", entityColumn = "id")
    val moneyRelation: MoneyRelation
) {
}