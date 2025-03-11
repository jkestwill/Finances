package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.OperationEntity


data class OperationRelation(
    @Embedded
    val operationEntity: OperationEntity,

    @Relation(MoneyEntity::class, parentColumn = "money_id", entityColumn = "id")
    val money:MoneyEntity
)

