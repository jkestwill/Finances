package com.jk.transaction_database.transaction.relations

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.jk.transaction_database.transaction.entity.GoodsEntity

@DatabaseView("SELECT * FROM goods " +
        "INNER JOIN goods_list ON goods_list.goods_id = goods.id " +
        "INNER JOIN operation ON goods_list.operation_id = operation.id")
data class GoodsPurchaseDTO(
    @ColumnInfo("id")
    val goodsId:String,
    @ColumnInfo("name")
    val goodsName:String,
    @ColumnInfo("goods_amount")
    val amount:Int,

)