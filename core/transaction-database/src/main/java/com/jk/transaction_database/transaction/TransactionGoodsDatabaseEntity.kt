package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goods")
data class TransactionGoodsDatabaseEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val amount: Int,
    @ColumnInfo("cost_id")
    val costId: String
)