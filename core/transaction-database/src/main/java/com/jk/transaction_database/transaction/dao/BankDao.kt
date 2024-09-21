package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jk.transaction_database.transaction.BankEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.relations.BankRelation
@Dao
abstract class BankDao (
    val transactionDatabase:TransactionDatabase
) {
    private val addressDao = transactionDatabase.getAddressDao()

    @Query("SELECT * FROM bank ORDER BY bank.name")
    abstract suspend fun getAll():List<BankRelation>

    @Query("SELECT EXISTS(SELECT id FROM bank WHERE bank.id==:bankId LIMIT 1)")
    abstract suspend fun isExist(bankId:String):Boolean

    @Insert(BankEntity::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(bankEntity: BankEntity)

    @Query("SELECT * FROM bank WHERE name==:name ")
    abstract suspend fun getBankRelationByName(name:String):BankRelation

    @Transaction
    suspend fun insertRelation(bankRelation: BankRelation){
        addressDao.insert(bankRelation.addressEntity)
        insert(bankRelation.bankEntity)
    }
}