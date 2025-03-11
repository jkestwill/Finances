package com.jk.transaction_database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "transaction",foreignKeys = [
    ForeignKey(
        entity = OperationEntity::class,
        childColumns  = ["operation_id"],
        parentColumns = ["id"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = TransactionTypeEntity::class,
        childColumns  = ["type_id"],
        parentColumns = ["id"],
    ),
    ForeignKey(
        entity = MoneyAccountEntity::class,
        childColumns  = ["money_account_id"],
        parentColumns = ["id"],
    ),
])
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val date: LocalDateTime,
    @ColumnInfo(name="operation_id")
    val operationId: String,
    @ColumnInfo(name="type_id")
    val typeId: String,
    @ColumnInfo(name="money_account_id")
    val moneyAccountId:String
)










