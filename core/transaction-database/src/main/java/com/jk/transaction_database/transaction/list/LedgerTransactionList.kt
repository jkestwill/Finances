package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.entity.LedgerEntity
import com.jk.transaction_database.transaction.entity.TransactionEntity

@Entity(tableName = "ledger_transaction_list", primaryKeys = ["transaction_id","ledger_id"], foreignKeys = [
    ForeignKey(
        TransactionEntity::class,
        parentColumns = ["id"],
        childColumns = ["transaction_id"]
    ),
    ForeignKey(
        LedgerEntity::class,
        parentColumns = ["id"],
        childColumns = ["ledger_id"],
    ),
])
data class LedgerTransactionList(
    @ColumnInfo("transaction_id")
    val transactionId:String,
    @ColumnInfo("ledger_id")
    val ledgerId:String
) {
}