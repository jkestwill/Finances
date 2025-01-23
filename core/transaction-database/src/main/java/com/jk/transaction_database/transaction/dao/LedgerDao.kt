package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.LedgerEntity

@Dao
interface LedgerDao {

    @Delete(entity = LedgerEntity::class)
    suspend fun delete(t: LedgerEntity)

    @Update(entity = LedgerEntity::class)
    suspend fun update(t: LedgerEntity)

    @Insert(entity = LedgerEntity::class)
    suspend fun insert(t: LedgerEntity)

    @Query(value = "SELECT * FROM ledger")
    suspend fun getAll(): List<LedgerEntity>

//    @Transaction
//    @Query(value = "SELECT * FROM ledger")
//    suspend fun getRelation(): List<LedgerRelation>
}