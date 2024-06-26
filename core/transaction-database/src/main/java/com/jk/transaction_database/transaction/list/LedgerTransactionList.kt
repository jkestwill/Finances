package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "ledger_transaction_list", primaryKeys = ["transaction_id","ledger_id"])
data class LedgerTransactionList(
    @ColumnInfo("transaction_id")
    val transactionId:String,
    @ColumnInfo("ledger_id")
    val ledgerId:String
) {
}