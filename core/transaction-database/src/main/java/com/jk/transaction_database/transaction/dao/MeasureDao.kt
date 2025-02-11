package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jk.transaction_database.transaction.entity.MeasureEntity

@Dao
interface MeasureDao {
    @Insert(MeasureEntity::class)
   suspend fun insert(measureEntity: MeasureEntity)

   @Query("SELECT * FROM measure")
   suspend fun getAll():List<MeasureEntity>
}