package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.SpecificationsEntity
import com.jk.transaction_database.transaction.GoodsEntity
import com.jk.transaction_database.transaction.MoneyEntity
import com.jk.transaction_database.transaction.list.GoodsSpecificationsListEntity

data class GoodsRelation(
    @Embedded
    val goodsEntity: GoodsEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity=SpecificationsEntity::class,
        associateBy = Junction(
            value = GoodsSpecificationsListEntity::class,
            parentColumn = "goods_id",
            entityColumn = "specifications_id"
        )
    )
    val specificationList: List<SpecificationRelation>,

    @Relation(
        parentColumn = "cost_id",
        entityColumn = "id",
        entity = MoneyEntity::class
    )
    val cost: MoneyRelation,
) {
}