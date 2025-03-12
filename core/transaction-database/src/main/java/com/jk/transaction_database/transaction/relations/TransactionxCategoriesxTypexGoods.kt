package com.jk.transaction_database.transaction.relations

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
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

@DatabaseView(
    value = "SELECT `transaction`.*, type.*, operation.*, category_list.*, category.*,goods_list.goods_id as goods_list_goods_id, goods_list.operation_id as goods_list_operation_id,goods_list.goods_amount, goods.id as goods_id, goods.name as goods_name FROM `transaction` " +
            "INNER JOIN type ON `transaction`.type_id == type.id " +
            "INNER JOIN operation ON `transaction`.operation_id==operation.id " +
            "INNER JOIN category_list ON operation.id == category_list.operation_id " +
            "INNER JOIN category ON category_list.category_id == category.id " +
            "LEFT JOIN goods_list ON goods_list.operation_id == operation.id " +
            "LEFT JOIN goods ON goods_list.goods_id = goods.id", viewName = "full_transaction"
)
data class TransactionxCategoriesxTypexGoods(
    @Embedded
    val transactionEntity: TransactionEntity,

    @Relation(
        entity = OperationEntity::class,
        parentColumn = "operation_id",
        entityColumn = "id",
        associateBy = Junction(OperationEntity::class, "id")
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
        entityColumn = "id",
        parentColumn = "id",
        associateBy = Junction(OperationGoodsListEntity::class, "operation_id", "goods_id")
    )
    val goodsList: List<GoodsPurchaseDTO>,
    )

