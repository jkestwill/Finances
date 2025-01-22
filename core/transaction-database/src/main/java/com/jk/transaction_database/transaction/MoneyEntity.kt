package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "money")
data class MoneyEntity(
    @PrimaryKey
    val id: String,
    val amount: Double,
    @ColumnInfo("currency_id")
    val currencyId:String
)