package com.jk.transaction_database.transaction.preview

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionEntity
import com.jk.transaction_database.transaction.TransactionTypeDatabaseEntity

class TransactionPreviewEntity(
    @Embedded
    val transaction: TransactionEntity,
    @Relation(entity = OperationEntity::class,entityColumn = "id", parentColumn = "id")
    val operationRelation: OperationPreviewEntity,
    @Relation(
        entityColumn = "id",
        parentColumn = "type_id"
    )
    val typeRelation: TransactionTypeDatabaseEntity

) {
}