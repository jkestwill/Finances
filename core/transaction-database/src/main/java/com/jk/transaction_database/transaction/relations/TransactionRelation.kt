package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionEntity
import com.jk.transaction_database.transaction.TransactionTypeDatabaseEntity

data class TransactionRelation(
    @Embedded
    val transaction: TransactionEntity,

    @Relation(entityColumn = "id", parentColumn = "operation_id", entity = OperationEntity::class)
    val operation: OperationRelation,

    @Relation(
        entityColumn = "id",
        parentColumn = "type_id"
    )
    val type: TransactionTypeDatabaseEntity
) {
}