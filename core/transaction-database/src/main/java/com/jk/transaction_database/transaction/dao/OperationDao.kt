package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.OperationEntity
import com.jk.transaction_database.transaction.relations.OperationRelation

@Dao
interface OperationDao {

    @Insert(entity = OperationEntity::class)
    suspend fun insert(t: OperationEntity)

    @Update(entity = OperationEntity::class)
    suspend fun update(t: OperationEntity)

    @Delete(entity = OperationEntity::class)
    suspend fun delete(t: OperationEntity)

    @Query("SELECT * FROM operation")
    suspend fun getAll(): List<OperationEntity>

    @Transaction
    @Query("SELECT * FROM operation")
    fun getRelation(): List<OperationRelation>
}