package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.TransactionGoodsDatabaseEntity
import com.jk.transaction_database.transaction.TransactionMoneyDatabaseEntity
import com.jk.transaction_database.transaction.TransactionScheduleDatabaseEntity
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
        entity=TransactionGoodsDatabaseEntity::class,
        associateBy = Junction(
            parentColumn  = "operation_id",
            entityColumn = "goods_id",
            value=  OperationGoodsListEntity::class,
            )
    )
    val goodsList: List<GoodsRelation>,

    @Relation(entity = TransactionMoneyDatabaseEntity::class,parentColumn = "money_id", entityColumn = "id")
    val cost: MoneyRelation,
    @Relation(parentColumn = "id", entityColumn = "id", associateBy = Junction(
        parentColumn = "operation_id", entityColumn = "schedule_id", value = OperationScheduleList::class
    ))
    val schedule: List<TransactionScheduleDatabaseEntity>
)