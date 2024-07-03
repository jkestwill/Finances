package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey()
    val id: String,
    val date: LocalDateTime,
    @ColumnInfo(name="operation_id")
    val operationId: String,
    @ColumnInfo(name="type_id")
    val typeId: String,
) {
}










