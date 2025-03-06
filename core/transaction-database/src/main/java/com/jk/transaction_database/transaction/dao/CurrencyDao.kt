package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.common_data.StringUUIDGenerator
import com.jk.common_data.sha256
import com.jk.money_common_data.Currencies
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import com.jk.transaction_database.transaction.entity.CurrencyEntity

@Dao
abstract class CurrencyDao internal constructor() {
    private var db:TransactionDatabase? = null
    internal constructor(db: TransactionDatabase):this(){
        this.db = db


    }

    @Delete(entity = CurrencyEntity::class)
    abstract suspend fun delete(t: CurrencyEntity)

    @Update(entity = CurrencyEntity::class,onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(t: CurrencyEntity)

    @Insert(entity = CurrencyEntity::class, onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(t: CurrencyEntity)

    @Query("SELECT * FROM currency")
    abstract suspend fun getAll(): List<CurrencyEntity>

    @Query("SELECT * FROM currency WHERE currency.name==:name")
    abstract suspend fun getOrNull(name: String): CurrencyEntity?

    @Transaction
    open suspend fun insertIfNotExist(currency: String) {
        if (getOrNull(currency) == null) {
            insert(CurrencyEntity(id = currency.sha256(), name = currency))
        }
    }
}