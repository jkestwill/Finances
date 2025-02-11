package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.entity.CategoryEntity
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.OperationEntity
import com.jk.transaction_database.transaction.entity.TransactionEntity
import com.jk.transaction_database.transaction.entity.TransactionTypeEntity
import com.jk.transaction_database.transaction.list.OperationCategoryList
import com.jk.transaction_database.transaction.list.OperationGoodsListEntity

data class TransactionxCategoriesxTypexGoods(
    @Embedded
    val transactionEntity: TransactionEntity,

    @Relation(
        entity = OperationEntity::class,
        parentColumn = "operation_id",
        entityColumn = "id",
        associateBy =Junction(OperationEntity::class,"id")
    )
    val operation: OperationRelation,
    @Relation(entity = TransactionTypeEntity::class, parentColumn = "type_id", "id")
    val type: TransactionTypeEntity,
    @Relation(
        entity = CategoryEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = OperationCategoryList::class,
            "operation_id",
            "category_id"
        )
    )
    val categoryList: List<CategoryEntity>,
    @Relation(
        GoodsEntity::class,
        entityColumn = "id",
        parentColumn = "operation_id",
        associateBy = Junction(OperationGoodsListEntity::class, "operation_id", "goods_id")
    )
    val goodsList: List<GoodsEntity>,

    )
