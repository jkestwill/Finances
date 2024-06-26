package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.OperationDatabaseEntity
import com.jk.transaction_database.transaction.TransactionDatabaseEntity
import com.jk.transaction_database.transaction.TransactionGoodsDatabaseEntity
import com.jk.transaction_database.transaction.TransactionTypeDatabaseEntity
import com.jk.transaction_database.transaction.list.TransactionGoodsList

data class TransactionRelation(
    @Embedded
    val transaction: TransactionDatabaseEntity,

    @Relation(entityColumn = "id", parentColumn = "operation_id", entity = OperationDatabaseEntity::class)
    val operation: OperationRelation,
    @Relation(
        parentColumn  = "id",
        entityColumn = "id",
        associateBy = Junction(
            parentColumn  = "transaction_id",
            entityColumn = "goods_id",
          value=  TransactionGoodsList::class,

        )
    )
    val goodsList: List<TransactionGoodsDatabaseEntity>,
    @Relation(
        entityColumn = "id",
        parentColumn = "type_id"
    )
    val type: TransactionTypeDatabaseEntity
) {
}