package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "category"
)
data class TransactionCategoryDatabaseEntity(
    @PrimaryKey
    val id:String,
    val name:String,
    @ColumnInfo(name="is_expenses")
    val isExpenses:Boolean,
    val color:String
) {
}