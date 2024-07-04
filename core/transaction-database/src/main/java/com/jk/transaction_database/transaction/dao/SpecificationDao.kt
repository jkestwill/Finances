package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import com.jk.transaction_database.transaction.SpecificationsEntity

@Dao
interface SpecificationDao {
    @Insert(SpecificationsEntity::class)
   suspend fun insert(specificationsEntity: SpecificationsEntity)
}