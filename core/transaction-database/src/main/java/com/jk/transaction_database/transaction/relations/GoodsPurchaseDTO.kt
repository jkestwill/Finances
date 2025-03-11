package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import com.jk.transaction_database.transaction.entity.GoodsEntity

data class GoodsPurchaseDTO(
    @Embedded
    val goodsEntity: GoodsEntity,
    val operationId:String,
    val goodsName:String,
    val goodsAmount:String,

)