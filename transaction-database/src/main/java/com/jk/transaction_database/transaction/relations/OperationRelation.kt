package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.OperationDatabaseEntity
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.TransactionMoneyDatabaseEntity
import com.jk.transaction_database.transaction.TransactionScheduleDatabaseEntity
import com.jk.transaction_database.transaction.list.OperationCategoryList

data class OperationRelation public constructor (
    @Embedded
    val operation: OperationDatabaseEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            parentColumn = "operation_id",
            entityColumn = "category_id",
            value = OperationCategoryList::class
        )
    )
    val categoryList: List<TransactionCategoryDatabaseEntity>,

    @Relation(entity = TransactionMoneyDatabaseEntity::class,parentColumn = "money_id", entityColumn = "id")
    val cost: MoneyRelation,
    @Relation(parentColumn = "schedule_id", entityColumn = "id")
    val schedule: TransactionScheduleDatabaseEntity
)