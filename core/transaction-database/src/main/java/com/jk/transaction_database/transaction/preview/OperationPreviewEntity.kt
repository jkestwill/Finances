package com.jk.transaction_database.transaction.preview

import androidx.room.Embedded
import com.jk.transaction_database.transaction.entity.OperationEntity


data class OperationPreviewEntity(
    @Embedded
    val operation: OperationEntity,

//    @Relation(entity = MoneyEntity::class,parentColumn = "money_id", entityColumn = "id")
//    val moneyRelation: MoneyRelation
) {
}