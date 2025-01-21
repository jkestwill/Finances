package com.jk.transaction_database.transaction.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.jk.transaction_database.transaction.LedgerEntity
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.TransactionEntity

@Entity(tableName = "ledger_transaction_list",  foreignKeys = [
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