package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.TransactionGoodsDatabaseEntity
import com.jk.transaction_database.transaction.relations.GoodsRelation

@Dao
interface GoodsDao  {
@Transaction
    @Update(entity = TransactionGoodsDatabaseEntity::class)
    suspend fun update(t: TransactionGoodsDatabaseEntity)

    @Delete(entity = TransactionGoodsDatabaseEntity::class)
     suspend fun delete(t: TransactionGoodsDatabaseEntity)

    @Query(value = "SELECT * FROM goods")
    suspend fun getAll(): List<GoodsRelation>

    @Insert(entity = TransactionGoodsDatabaseEntity::class)
     suspend fun insert(t: TransactionGoodsDatabaseEntity)

    @Insert(entity = TransactionGoodsDatabaseEntity::class)
     suspend fun insert(t: List<TransactionGoodsDatabaseEntity>)
}