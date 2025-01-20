package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jk.transaction_database.transaction.TransactionScheduleEntity

@Dao
interface ScheduleDao {

    @Update(entity = TransactionScheduleEntity::class)
    suspend fun update(t: TransactionScheduleEntity)


    @Insert(entity = TransactionScheduleEntity::class)
    suspend fun insert(t: List<TransactionScheduleEntity>)


    @Delete(entity = TransactionScheduleEntity::class)
    suspend fun delete(t: TransactionScheduleEntity)


    @Query(value = "SELECT * FROM schedule")
    suspend fun getAll(): List<TransactionScheduleEntity>

}