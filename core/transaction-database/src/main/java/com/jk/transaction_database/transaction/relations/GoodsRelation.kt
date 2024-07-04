package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.SpecificationsEntity
import com.jk.transaction_database.transaction.TransactionGoodsDatabaseEntity
import com.jk.transaction_database.transaction.TransactionMoneyDatabaseEntity
import com.jk.transaction_database.transaction.list.GoodsSpecificationsListEntity

data class GoodsRelation(
    @Embedded
    val goodsEntity: TransactionGoodsDatabaseEntity,
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
        entity = TransactionMoneyDatabaseEntity::class
    )
    val cost: MoneyRelation
) {
}