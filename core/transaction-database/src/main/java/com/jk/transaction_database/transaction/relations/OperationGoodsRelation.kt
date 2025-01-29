package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.GoodsEntity
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.list.OperationGoodsListEntity


/**
 * Operations with goods list
 * */
data class OperationGoodsRelation(
    @Embedded
    val operation: OperationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            OperationGoodsListEntity::class,
            parentColumn = "operation_id",
            entityColumn = "goods_id"
        )
    )
    val goodsList: List<GoodsEntity>
)