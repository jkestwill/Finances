package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jk.transaction_database.transaction.list.LedgerTransactionList

@Dao
interface LedgerTransactionListDao {
    @Delete(entity = LedgerTransactionList::class)
    suspend fun delete(t: LedgerTransactionList)

    @Update(entity = LedgerTransactionList::class)
    suspend fun update(t: LedgerTransactionList)

    @Insert(entity = LedgerTransactionList::class)
    suspend fun insert(t: LedgerTransactionList)

    @Query("SELECT * FROM ledger_transaction_list")
    suspend fun getAll(): List<LedgerTransactionList>
}