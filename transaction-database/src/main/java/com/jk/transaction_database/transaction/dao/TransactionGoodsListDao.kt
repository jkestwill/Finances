package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jk.transaction_database.transaction.list.TransactionGoodsList

@Dao
interface TransactionGoodsListDao {
    @Delete(entity = TransactionGoodsList::class)
    suspend fun delete(t: TransactionGoodsList)

    @Insert(entity = TransactionGoodsList::class)
    suspend fun insert(t: TransactionGoodsList)

    @Insert(entity = TransactionGoodsList::class)
    suspend fun insert(t: List<TransactionGoodsList>)

    @Update(entity = TransactionGoodsList::class)
    suspend fun update(t: TransactionGoodsList)

    @Query(value = "SELECT * FROM goods_list")
    suspend fun getAll(): List<TransactionGoodsList>
}