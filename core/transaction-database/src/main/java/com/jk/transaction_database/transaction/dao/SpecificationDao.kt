package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jk.transaction_database.transaction.entity.SpecificationsEntity

@Dao
interface SpecificationDao {
    @Insert(SpecificationsEntity::class)
    suspend fun insert(specificationsEntity: SpecificationsEntity)

    @Insert(SpecificationsEntity::class)
    suspend fun insert(specificationsEntity: List<SpecificationsEntity>)
    @Query("SELECT * FROM specifications")
    suspend fun getAll():List<SpecificationsEntity>
}