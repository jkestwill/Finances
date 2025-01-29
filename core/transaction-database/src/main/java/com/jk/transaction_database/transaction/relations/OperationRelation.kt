package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.list.OperationGoodsListEntity


data class OperationRelation(
    val operationGoodsListEntity: OperationGoodsListEntity,

)
