package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jk.transaction_database.transaction.entity.ScheduleEntity

@Dao
interface ScheduleDao {

    @Update(entity = ScheduleEntity::class)
    suspend fun update(t: ScheduleEntity)


    @Insert(entity = ScheduleEntity::class)
    suspend fun insert(t: List<ScheduleEntity>)


    @Delete(entity = ScheduleEntity::class)
    suspend fun delete(t: ScheduleEntity)


    @Query(value = "SELECT * FROM schedule")
    suspend fun getAll(): List<ScheduleEntity>

}