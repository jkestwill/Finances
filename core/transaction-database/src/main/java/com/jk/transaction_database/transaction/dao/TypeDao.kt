package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.entity.TransactionTypeEntity

@Dao
interface TypeDao {
    @Delete(entity = TransactionTypeEntity::class)
    suspend fun delete(t: TransactionTypeEntity)

    @Insert(entity = TransactionTypeEntity::class)
    suspend fun insert(t: TransactionTypeEntity)

    @Transaction
    suspend fun insertIfNotExist(transactionTypeEntity: TransactionTypeEntity) {
        if (getByName(transactionTypeEntity.name) == null) {
            insert(transactionTypeEntity)
        }
    }

    @Update(entity = TransactionTypeEntity::class)
    suspend fun update(t: TransactionTypeEntity)

    @Query(value = "SELECT * FROM type")
    suspend fun getAll(): List<TransactionTypeEntity>

    @Query("SELECT name FROM type WHERE name=:name")
    suspend fun getByName(name: String): String?

}