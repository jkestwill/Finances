package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity

// add ledger
@Entity(
    tableName="goods_list",
    primaryKeys = ["transaction_id","goods_id"]

)
data class TransactionGoodsList(
    @ColumnInfo(name = "transaction_id")
    val transactionId: String,
    @ColumnInfo(name = "goods_id")
    val goodsId: String
)