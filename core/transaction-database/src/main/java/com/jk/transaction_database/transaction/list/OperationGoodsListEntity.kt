package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity

// add ledger
@Entity(
    tableName="goods_list",
    primaryKeys = ["operation_id","goods_id"]

)
data class OperationGoodsListEntity(
    @ColumnInfo(name = "operation_id")
    val operationId: String,
    @ColumnInfo(name = "goods_id")
    val goodsId: String
)