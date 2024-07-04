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
interface GoodsDao {
    @Transaction
    @Update(entity = TransactionGoodsDatabaseEntity::class)
    suspend fun update(t: TransactionGoodsDatabaseEntity)

    @Delete(entity = TransactionGoodsDatabaseEntity::class)
    suspend fun delete(t: TransactionGoodsDatabaseEntity)

    @Query(
        value = "SELECT * FROM goods WHERE LOWER(goods.name) LIKE  '%'||:q||'%' " +
                "ORDER BY CASE WHEN :isAsc ==1 THEN :sortBy END ASC, " +
                "CASE WHEN :isAsc ==0 THEN :sortBy END DESC " +
                "LIMIT :limit OFFSET :offset"
    )
   suspend fun getAll(
        q: String,
        sortBy: String,
        isAsc: Boolean,
        limit: Int,
        offset: Int
    ): List<GoodsRelation>

    @Insert(entity = TransactionGoodsDatabaseEntity::class)
    suspend fun insert(t: TransactionGoodsDatabaseEntity)

    @Insert(entity = TransactionGoodsDatabaseEntity::class)
    suspend fun insert(t: List<TransactionGoodsDatabaseEntity>)
}