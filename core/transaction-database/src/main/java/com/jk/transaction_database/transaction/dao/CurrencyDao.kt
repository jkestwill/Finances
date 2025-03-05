package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.common_data.sha256
import com.jk.transaction_database.transaction.entity.CurrencyEntity

@Dao
interface CurrencyDao {
    @Delete(entity = CurrencyEntity::class)
    suspend fun delete(t: CurrencyEntity)

    @Update(entity = CurrencyEntity::class)
    suspend fun update(t: CurrencyEntity)

    @Insert(entity = CurrencyEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(t: CurrencyEntity)

    @Query("SELECT * FROM currency")
    suspend fun getAll(): List<CurrencyEntity>

    @Query("SELECT * FROM currency WHERE currency.name==:name")
    suspend fun getOrNull(name: String): CurrencyEntity?

    @Transaction
    suspend fun insertIfNotExist(currency: String) {
        if (getOrNull(currency) == null) {
            insert(CurrencyEntity(id = currency.sha256(), name = currency))
        }
    }
}