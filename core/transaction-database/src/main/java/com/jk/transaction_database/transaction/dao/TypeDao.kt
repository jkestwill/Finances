package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.TransactionTypeDatabaseEntity

@Dao
interface TypeDao {
    @Delete(entity = TransactionTypeDatabaseEntity::class)
    suspend fun delete(t: TransactionTypeDatabaseEntity)

    @Insert(entity = TransactionTypeDatabaseEntity::class)
    suspend fun insert(t: TransactionTypeDatabaseEntity)

    @Transaction
    suspend fun insertIfNotExist(transactionTypeDatabaseEntity: TransactionTypeDatabaseEntity) {
        if (getByName(transactionTypeDatabaseEntity.name) == null) {
            insert(transactionTypeDatabaseEntity)
        }
    }

    @Update(entity = TransactionTypeDatabaseEntity::class)
    suspend fun update(t: TransactionTypeDatabaseEntity)

    @Query(value = "SELECT * FROM type")
    suspend fun getAll(): List<TransactionTypeDatabaseEntity>

    @Query("SELECT name FROM type WHERE name=:name")
    suspend fun getByName(name: String): String?

}