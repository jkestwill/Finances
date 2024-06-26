package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jk.transaction_database.transaction.TransactionTypeDatabaseEntity

@Dao
interface TypeDao {
    @Delete(entity = TransactionTypeDatabaseEntity::class)
   suspend fun delete(t: TransactionTypeDatabaseEntity)

    @Insert(entity = TransactionTypeDatabaseEntity::class)
     suspend fun insert(t: TransactionTypeDatabaseEntity)

    @Update(entity = TransactionTypeDatabaseEntity::class)
    suspend fun update(t: TransactionTypeDatabaseEntity)

    @Query(value = "SELECT * FROM type")
     suspend fun getAll(): List<TransactionTypeDatabaseEntity>

}