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
abstract class MoneyAccountDao internal constructor(private val db: TransactionDatabase) {
    private val moneyDao = db.getMoneyDao()

    @Insert(entity = MoneyAccountEntity::class)
    abstract fun insert(moneyAccountEntity: MoneyAccountEntity)

    @Transaction
    open suspend fun insert(moneyAccountDTO: MoneyAccountDTO) {
        moneyDao.insert(moneyAccountDTO.moneyDto.moneyEntity)
        insert(
            MoneyAccountEntity(
                id = moneyAccountDTO.id,
                name = moneyAccountDTO.name,
                imgPath = null,
                moneyId = moneyAccountDTO.moneyDto.moneyEntity.id
            )
        )
    }

    @Query("DELETE FROM money_account WHERE money_account.id == :id")
    abstract fun deleteById(id: String)

    @Query("SELECT * FROM money_account WHERE money_account.id == :id")
    abstract fun getById(id: String): MoneyAccountEntity

    @Query("SELECT * FROM money_account")
    abstract fun getAll(): MoneyAccountEntity

    @Query(
        "SELECT money.id as money_id, money.currency_id as money_currency_id, money.amount as money_amount,currency.id as currency_id,currency.name as currency_name,money_account.id as money_account_id, money_account.name as money_account_name, money_account.money_id, money_account.img_path FROM money_account " +
                "INNER JOIN money ON money_account.money_id == money.id " +
                "INNER JOIN currency ON money.currency_id == currency.id"
    )
    abstract suspend fun getAllDTO(): List<MoneyAccountDTO>

    @Query(
        "UPDATE MONEY SET amount=:amount WHERE " +
                "(SELECT money_id FROM money_account) == :moneyAccountId"
    )
    abstract fun updateAmount(moneyAccountId: String, amount: Double)

    @Query("DELETE FROM money_account")
    abstract suspend fun deleteAll()
}