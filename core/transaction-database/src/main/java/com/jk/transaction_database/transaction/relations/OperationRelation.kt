package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.GoodsEntity
import com.jk.transaction_database.transaction.MoneyEntity
import com.jk.transaction_database.transaction.TransactionScheduleEntity
import com.jk.transaction_database.transaction.list.OperationCategoryList
import com.jk.transaction_database.transaction.list.OperationScheduleList
import com.jk.transaction_database.transaction.list.OperationGoodsListEntity

data class OperationRelation(
    @Embedded
    val operation: OperationEntity,

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

    @Relation(
        parentColumn  = "id",
        entityColumn = "id",
        entity=GoodsEntity::class,
        associateBy = Junction(
            parentColumn  = "operation_id",
            entityColumn = "goods_id",
            value=  OperationGoodsListEntity::class,
            )
    )
    val goodsList: List<GoodsRelation>,

    @Relation(entity = MoneyEntity::class,parentColumn = "money_id", entityColumn = "id")
    val cost: MoneyRelation,
    @Relation(parentColumn = "id", entityColumn = "id", associateBy = Junction(
        parentColumn = "operation_id", entityColumn = "schedule_id", value = OperationScheduleList::class
    ))
    val schedule: List<TransactionScheduleEntity>
)