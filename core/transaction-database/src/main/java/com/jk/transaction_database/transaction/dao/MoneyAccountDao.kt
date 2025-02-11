package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jk.transaction_database.transaction.entity.MoneyAccountEntity

@Dao
abstract class MoneyAccountDao() {
    @Insert(entity = MoneyAccountEntity::class)
    abstract fun insert(moneyAccountEntity: MoneyAccountEntity)

    @Query("DELETE FROM money_account WHERE money_account.id == :id")
    abstract fun deleteById(id: String)

    @Query("SELECT * FROM money_account WHERE money_account.id == :id")
    abstract fun getById(id: String): MoneyAccountEntity

    @Query("UPDATE MONEY SET amount=:amount WHERE " +
            "(SELECT money_id FROM money_account) == :moneyAccountId")
    abstract fun updateAmount(moneyAccountId:String,amount:Double)

}