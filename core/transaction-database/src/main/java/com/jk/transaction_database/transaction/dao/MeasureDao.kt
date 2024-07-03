package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import com.jk.transaction_database.transaction.MeasureEntity

@Dao
interface MeasureDao {
    @Insert(MeasureEntity::class)
    fun insert(measureEntity: MeasureEntity)
}