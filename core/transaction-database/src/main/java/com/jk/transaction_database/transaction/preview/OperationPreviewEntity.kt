package com.jk.transaction_database.transaction.preview

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.entity.OperationEntity
import com.jk.transaction_database.transaction.relations.MoneyDTO


data class OperationPreviewEntity(
    @Embedded
    val operation: OperationEntity,

    @Relation(entity = MoneyEntity::class,parentColumn = "money_id", entityColumn = "id" )
    val moneyRelation: MoneyDTO
) {
}
