package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jk.transaction_database.transaction.list.OperationGoodsListEntity

@Dao
interface TransactionGoodsListDao {
    @Delete(entity = OperationGoodsListEntity::class)
    suspend fun delete(t: OperationGoodsListEntity)

    @Insert(entity = OperationGoodsListEntity::class)
    suspend fun insert(t: OperationGoodsListEntity)

    @Insert(entity = OperationGoodsListEntity::class)
    suspend fun insert(t: List<OperationGoodsListEntity>)

    @Update(entity = OperationGoodsListEntity::class)
    suspend fun update(t: OperationGoodsListEntity)

    @Query(value = "SELECT * FROM goods_list")
    suspend fun getAll(): List<OperationGoodsListEntity>
}