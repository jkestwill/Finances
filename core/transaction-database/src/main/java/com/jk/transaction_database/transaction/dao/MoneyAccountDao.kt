package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.entity.MoneyAccountEntity
import com.jk.transaction_database.transaction.relations.MoneyAccountDTO

@Dao
abstract class MoneyAccountDao internal constructor(private val db:TransactionDatabase) {
   //todo написать инсерт для говна

    @Insert(entity = MoneyAccountEntity::class)
    abstract fun insert(moneyAccountEntity: MoneyAccountEntity)

    @Transaction
    suspend fun insert(moneyAccountDTO:MoneyAccountDTO){
        insert(moneyAccountDTO.moneyDto.moneyEntity)
    }

    @Query("DELETE FROM money_account WHERE money_account.id == :id")
    abstract fun deleteById(id: String)

    @Query("SELECT * FROM money_account WHERE money_account.id == :id")
    abstract fun getById(id: String): MoneyAccountEntity

    @Query("SELECT * FROM money_account")
    abstract fun getAll():MoneyAccountEntity

    @Query("SELECT money.*,currency.*,money_account.id as money_account_id, money_account.name as money_account_name, money_account.money_id, money_account.img_path FROM money_account " +
            "INNER JOIN money ON money_account.money_id == money.id " +
            "INNER JOIN currency ON money.currency_id == currency.id")
    abstract fun getAllDTO():List<MoneyAccountDTO>

    @Query("UPDATE MONEY SET amount=:amount WHERE " +
            "(SELECT money_id FROM money_account) == :moneyAccountId")
    abstract fun updateAmount(moneyAccountId:String,amount:Double)

}