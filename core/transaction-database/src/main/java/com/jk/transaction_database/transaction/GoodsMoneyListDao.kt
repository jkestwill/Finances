package com.jk.transaction_database.transaction

import androidx.room.Dao
import androidx.room.Insert
import com.jk.transaction_database.transaction.list.GoodsMoneyListEntity

@Dao
interface GoodsMoneyListDao {
    @Insert(GoodsMoneyListEntity::class)
    suspend fun insert(goodsMoneyListEntity: List<GoodsMoneyListEntity>)

    @Insert(GoodsMoneyListEntity::class)
    suspend fun insert(goodsMoneyListEntity: GoodsMoneyListEntity)
}