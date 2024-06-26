package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.OperationDatabaseEntity
import com.jk.transaction_database.transaction.relations.OperationRelation

@Dao
interface OperationDao {

    @Insert(entity = OperationDatabaseEntity::class)
    suspend fun insert(t: OperationDatabaseEntity)

    @Update(entity = OperationDatabaseEntity::class)
    suspend fun update(t: OperationDatabaseEntity)

    @Delete(entity = OperationDatabaseEntity::class)
    suspend fun delete(t: OperationDatabaseEntity)

    @Query("SELECT * FROM operation")
    suspend fun getAll(): List<OperationDatabaseEntity>

    @Transaction
    @Query("SELECT * FROM operation")
    fun getRelation(): List<OperationRelation>
}