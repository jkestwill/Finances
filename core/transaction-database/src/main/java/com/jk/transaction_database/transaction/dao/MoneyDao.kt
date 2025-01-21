package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jk.transaction_database.transaction.MoneyEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.relations.MoneyRelation

@Dao
abstract class MoneyDao internal constructor(
    private val db: TransactionDatabase

) {
    private val currencyDao: CurrencyDao = db.getCurrencyDao()

    @Delete(entity = MoneyEntity::class)
    abstract suspend fun delete(t: MoneyEntity)

    @Update(entity = MoneyEntity::class)
    abstract suspend fun update(t: MoneyEntity)

    @Insert(entity = MoneyEntity::class)
    abstract suspend fun insert(t: MoneyEntity)

    @Query(value = "SELECT * FROM money")
    abstract suspend fun getAll(): List<MoneyEntity>

    @Transaction
    open suspend fun insert(money: MoneyRelation) {
        insert(money.money)
        currencyDao.insertIfNotExist(money.currency.name)
    }

    @Transaction
    @Query(value = "SELECT * FROM money")
    abstract suspend fun getRelation(): List<MoneyRelation>
}