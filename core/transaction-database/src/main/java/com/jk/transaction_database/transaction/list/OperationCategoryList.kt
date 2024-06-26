package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.DeleteTable
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.OperationDatabaseEntity
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity

@Entity(
    tableName="category_list",

    primaryKeys = ["operation_id","category_id"],
    foreignKeys = [ForeignKey(OperationDatabaseEntity::class, parentColumns = ["id"], childColumns = ["operation_id"]),
    ForeignKey(TransactionCategoryDatabaseEntity::class, parentColumns = ["id"], childColumns = ["category_id"], onDelete = ForeignKey.CASCADE),]
)
data class OperationCategoryList(
    @ColumnInfo(name = "operation_id")
    val operationId: String,
    @ColumnInfo(name = "category_id")
    val categoryId: String
)

