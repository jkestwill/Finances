package com.jk.transaction_database.transaction.preview

import androidx.room.Embedded
import com.jk.transaction_database.transaction.entity.OperationEntity
import com.jk.transaction_database.transaction.relations.MoneyDTO


data class OperationPreviewDTO(
    @Embedded("op_")
    val operation: OperationEntity,
    @Embedded("op_cost_")
    val moneyRelation: MoneyDTO
) {
}
