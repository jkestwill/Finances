package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.common_data.sha256
import com.jk.transaction_database.transaction.TransactionCurrencyDatabaseEntity

@Dao
interface CurrencyDao {
    @Delete(entity = TransactionCurrencyDatabaseEntity::class)
    suspend fun delete(t: TransactionCurrencyDatabaseEntity)

    @Update(entity = TransactionCurrencyDatabaseEntity::class)
    suspend fun update(t: TransactionCurrencyDatabaseEntity)

    @Insert(entity = TransactionCurrencyDatabaseEntity::class)
    suspend fun insert(t: TransactionCurrencyDatabaseEntity)

    @Query("SELECT * FROM currency")
    suspend fun getAll(): List<TransactionCurrencyDatabaseEntity>

    @Query("SELECT * FROM currency WHERE currency.name==:name")
    suspend fun getOrNull(name: String): TransactionCurrencyDatabaseEntity?

    @Transaction
    suspend fun insertIfNotExist(currency: String) {
        if (getOrNull(currency) == null) {
            insert(TransactionCurrencyDatabaseEntity(id = currency.sha256(), name = currency))
        }
    }
}