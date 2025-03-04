package com.jk.transaction_database.transaction.preview

import androidx.room.ColumnInfo

data class GoodsPreviewEntity(
    val id:String,
    val name:String,
    @ColumnInfo("money_amount")
    val cost: Double,
    @ColumnInfo("currency_name")
    val currency:String
)