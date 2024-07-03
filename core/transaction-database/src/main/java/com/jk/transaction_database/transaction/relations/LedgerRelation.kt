package com.jk.transaction_database.transaction.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jk.transaction_database.transaction.LedgerEntity
import com.jk.transaction_database.transaction.TransactionEntity
import com.jk.transaction_database.transaction.list.LedgerTransactionList

data class LedgerRelation(
    @Embedded
    val ledger: LedgerEntity,
    @Relation(
        entityColumn = "id",
        parentColumn = "id",
        entity= TransactionEntity::class,
        associateBy = Junction(
            parentColumn = "ledger_id",
            entityColumn = "transaction_id",
            value = LedgerTransactionList::class
        )
    )
    val transactionList:List<TransactionRelation>,

    ) {
}