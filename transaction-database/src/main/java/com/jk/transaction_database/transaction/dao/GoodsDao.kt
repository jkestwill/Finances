package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jk.transaction_database.transaction.TransactionGoodsDatabaseEntity

@Dao
interface GoodsDao  {

    @Update(entity = TransactionGoodsDatabaseEntity::class)
    suspend fun update(t: TransactionGoodsDatabaseEntity)

    @Delete(entity = TransactionGoodsDatabaseEntity::class)
     suspend fun delete(t: TransactionGoodsDatabaseEntity)

    @Query(value = "SELECT * FROM goods")
    suspend fun getAll(): List<TransactionGoodsDatabaseEntity>

    @Insert(entity = TransactionGoodsDatabaseEntity::class)
     suspend fun insert(t: TransactionGoodsDatabaseEntity)

    @Insert(entity = TransactionGoodsDatabaseEntity::class)
     suspend fun insert(t: List<TransactionGoodsDatabaseEntity>)
}