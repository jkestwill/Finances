package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity("goods_specifications_list", primaryKeys = ["goods_id","specifications_id"])
data class GoodsSpecificationsListEntity(
    @ColumnInfo("goods_id")
    val goodsId:String,
    @ColumnInfo("specifications_id")
    val specificationsId:String
)
