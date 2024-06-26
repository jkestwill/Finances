package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.TransactionMoneyDatabaseEntity
import com.jk.transaction_database.transaction.relations.MoneyRelation

@Dao
interface MoneyDao  {

    @Delete(entity = TransactionMoneyDatabaseEntity::class)
    suspend fun delete(t: TransactionMoneyDatabaseEntity)

    @Update(entity = TransactionMoneyDatabaseEntity::class)
    suspend fun update(t: TransactionMoneyDatabaseEntity)

    @Insert(entity = TransactionMoneyDatabaseEntity::class)
    suspend fun insert(t: TransactionMoneyDatabaseEntity)

    @Query(value = "SELECT * FROM money")
    suspend fun getAll(): List<TransactionMoneyDatabaseEntity>

    @Transaction
    @Query(value = "SELECT * FROM money")
    suspend fun getRelation(): List<MoneyRelation>
}