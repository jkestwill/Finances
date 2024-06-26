package com.jk.transaction_database.transaction.preview

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.OperationDatabaseEntity
import com.jk.transaction_database.transaction.TransactionDatabaseEntity
import com.jk.transaction_database.transaction.TransactionTypeDatabaseEntity

class TransactionPreviewEntity(
    @Embedded
    val transaction: TransactionDatabaseEntity,
    @Relation(entity = OperationDatabaseEntity::class,entityColumn = "id", parentColumn = "id")
    val operation: OperationPreviewEntity,
    @Relation(
        entityColumn = "id",
        parentColumn = "type_id"
    )
    val type: TransactionTypeDatabaseEntity

) {
}