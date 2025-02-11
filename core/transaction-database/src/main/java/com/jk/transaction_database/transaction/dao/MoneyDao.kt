package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jk.transaction_database.transaction.entity.MoneyEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase

@Dao
abstract class MoneyDao internal constructor(
    private val db: TransactionDatabase

) {
    private val currencyDao: CurrencyDao = db.getCurrencyDao()

    @Delete(entity = MoneyEntity::class)
    abstract suspend fun delete(t: MoneyEntity)

    @Update(entity = MoneyEntity::class)
    abstract suspend fun update(t: MoneyEntity)

    @Insert(entity = MoneyEntity::class, onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(t: MoneyEntity)

    @Query(value = "SELECT * FROM money")
    abstract suspend fun getAll(): List<MoneyEntity>

//    @Transaction
//    open suspend fun insert(money: MoneyRelation) {
//        insert(money.money)
//        currencyDao.insertIfNotExist(money.currency.name)
//    }

//    @Transaction
//    @Query(value = "SELECT * FROM money")
//    abstract suspend fun getRelation(): List<MoneyRelation>
}