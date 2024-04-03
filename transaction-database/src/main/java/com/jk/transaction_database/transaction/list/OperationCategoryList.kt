package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName="category_list",
    primaryKeys = ["operation_id","category_id"]
)
data class OperationCategoryList(
    @ColumnInfo(name = "operation_id")
    val operationId: String,
    @ColumnInfo(name = "category_id")
    val categoryId: String
)

