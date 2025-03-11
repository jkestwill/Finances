package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jk.transaction_database.transaction.list.OperationGoodsListEntity

@Dao
interface OperationGoodsListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(operationGoodsListEntity: OperationGoodsListEntity)
}