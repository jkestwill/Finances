package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.util.TableInfo
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity

@Entity(
    tableName = "category_list",
    primaryKeys = ["operation_id", "category_id"],
    foreignKeys = [
        ForeignKey(
            OperationEntity::class,
            parentColumns = ["id"],
            childColumns = ["operation_id"]
        ),
        ForeignKey(
            TransactionCategoryDatabaseEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class OperationCategoryList(
    @ColumnInfo(name = "operation_id") val operationId: String,
    @ColumnInfo(name = "category_id") val categoryId: String
)

