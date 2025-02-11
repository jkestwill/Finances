package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jk.transaction_database.transaction.list.GoodsSpecificationsListEntity

@Dao
interface GoodsSpecificationDao {
    @Insert(GoodsSpecificationsListEntity::class)
    suspend fun insert(specificationList:GoodsSpecificationsListEntity)
    @Query("SELECT * FROM goods_specifications_list")
    suspend fun getAll():List<GoodsSpecificationsListEntity>
}