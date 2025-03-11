package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.entity.GoodsEntity
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.SpecificationsEntity
import com.jk.transaction_database.transaction.list.GoodsMoneyListEntity
import com.jk.transaction_database.transaction.list.GoodsSpecificationsListEntity
// todo или новая моделька без листа либо убрать лист
data class GoodsxSpecificationsxMoneyRelation(
    @Embedded
    val goodsEntity: GoodsEntity,
    @Relation(
        entityColumn = "id", parentColumn = "id",
        associateBy = Junction(
            entityColumn = "specifications_id",
            parentColumn = "goods_id",
            value = GoodsSpecificationsListEntity::class
        )
    )
    val specifications: List<SpecificationsEntity>?,
    @Relation(
        entity = MoneyEntity::class,
        entityColumn = "id",
        parentColumn = "id",
        associateBy = Junction(
            GoodsMoneyListEntity::class,
            parentColumn = "goods_id",
            entityColumn = "money_id"
        )
    )
    val cost: List<MoneyEntity>
)