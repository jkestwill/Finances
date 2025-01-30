package com.jk.transaction_database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "operation", foreignKeys = [
        ForeignKey(
            entity = MoneyEntity::class,
            childColumns = ["money_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OperationEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    @ColumnInfo("money_id")
    val moneyId: String,
    @ColumnInfo(name = "is_expenses")
    val isExpenses: Boolean,
)