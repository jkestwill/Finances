package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.CategoryEntity
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionEntity
import com.jk.transaction_database.transaction.TransactionTypeEntity
import com.jk.transaction_database.transaction.list.OperationCategoryList

data class OperationTransactionRelation(
    @Embedded
    val transactionEntity: TransactionEntity,

    @Relation(entity = OperationEntity::class, parentColumn = "operation_id", entityColumn = "id")
    val operation:OperationEntity,
    @Relation(entity = TransactionTypeEntity::class, parentColumn = "type_id","id")
    val type:TransactionTypeEntity,
    @Relation(entity = CategoryEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = OperationCategoryList::class,
            "operation_id",
            "category_id"
        )
    )
    val categoryList:List<CategoryEntity>
)
