package com.jk.transaction_database.transaction.preview

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.entity.CategoryEntity
import com.jk.transaction_database.transaction.entity.OperationEntity
import com.jk.transaction_database.transaction.entity.TransactionEntity
import com.jk.transaction_database.transaction.entity.TransactionTypeEntity
import com.jk.transaction_database.transaction.list.OperationCategoryList

data class TransactionPreviewRelation(
    @Embedded("tr_")
    val transactionEntity: TransactionEntity,
    @Embedded
    val operationEntity: OperationPreviewDTO,
    @Embedded("type_")
    val typeEntity: TransactionTypeEntity,

    @Relation(entity = CategoryEntity::class,
        parentColumn = "tr_operation_id",
        entityColumn = "id",
        associateBy = Junction(
            value = OperationCategoryList::class,
            parentColumn = "operation_id",
            entityColumn = "category_id"
        )
    )
    val categoryList:List<CategoryEntity>

)
