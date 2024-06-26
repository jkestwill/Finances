package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jk.transaction_database.transaction.TransactionScheduleDatabaseEntity

@Dao
interface ScheduleDao {

    @Update(entity = TransactionScheduleDatabaseEntity::class)
    suspend fun update(t: TransactionScheduleDatabaseEntity)


    @Insert(entity = TransactionScheduleDatabaseEntity::class)
    suspend fun insert(t: TransactionScheduleDatabaseEntity)


    @Delete(entity = TransactionScheduleDatabaseEntity::class)
    suspend fun delete(t: TransactionScheduleDatabaseEntity)


    @Query(value = "SELECT * FROM schedule")
    suspend fun getAll(): List<TransactionScheduleDatabaseEntity>


}