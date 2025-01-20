package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jk.common_data.SearchParams
import com.jk.transaction_database.transaction.ExchangeRateEntity
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.relations.ExchangeRateRelation
import java.time.LocalDateTime

@Dao
abstract class ExchangeRateDao internal constructor (
    private val transactionDatabase: TransactionDatabase
) {
    private val bankDao = transactionDatabase.getBankDao()

    @Insert(entity = ExchangeRateEntity::class)
    abstract suspend fun insert(exchangeRateEntity: ExchangeRateEntity)


    @Query(
        value = "" +
                "SELECT * FROM exchange_rate " +
                "INNER JOIN currency c_from ON c_from.id == exchange_rate.currency_from_id " +
                "INNER JOIN currency c_to ON c_to.id == exchange_rate.currency_to_id " +
                "ORDER BY exchange_rate.date DESC " +
                "LIMIT 1"
    )
    abstract suspend fun getRelevantExchangeList(): ExchangeRateRelation?


    @Query(
        "INSERT INTO exchange_rate VALUES(:id," +
                "CASE WHEN EXISTS (SELECT * FROM currency WHERE currency.name==:currencyNameIn) THEN (SELECT id FROM currency WHERE name==:currencyNameIn) " +
                "ELSE :currencyNameIn END ," +
                " CASE WHEN EXISTS (SELECT * FROM  currency WHERE currency.name==:currencyNameOut) THEN (SELECT id FROM currency WHERE name==:currencyNameIn) " +
                "ELSE :currencyNameOut END," +
                " :date," +
                " :rate," +
                ":scale," +
                ":bankId) "
    )

   abstract  suspend fun insert(
        id: String,
        currencyNameIn: String,
        currencyNameOut: String,
        date: LocalDateTime,
        scale: Int,
        rate: Double,
        bankId:String?
    )

   @Transaction
   open suspend fun insertRelation(exchangeRateRelation: ExchangeRateRelation){

       if(exchangeRateRelation.bankRelation!=null && !bankDao.isExist(exchangeRateRelation.bankRelation.bankEntity.id)) {
            bankDao.insertRelation(exchangeRateRelation.bankRelation)
       }
       with(exchangeRateRelation){
           insert(
               id=exchangeRateEntity.id,
               currencyNameIn =currencyFrom.name,
               currencyNameOut = currencyTo.name,
               date=exchangeRateEntity.date,
               scale=exchangeRateEntity.scale,
               rate =exchangeRateEntity.rate,
               bankId=bankRelation?.bankEntity?.id
           )
       }
    }

    @Query("SELECT * FROM exchange_rate WHERE exchange_rate.id LIKE :id")
    abstract suspend fun getOrNull(id: String): ExchangeRateEntity?

    @Query("SELECT * FROM exchange_rate " +
            "INNER JOIN currency as c1 ON c1.id == exchange_rate.currency_from_id " +
            "INNER JOIN currency as c2 ON c2.id == exchange_rate.currency_to_id" +
            " WHERE c1.name LIKE :currencyIn AND c2.name LIKE :currencyOut AND exchange_rate.date==:date ")
    abstract suspend fun getList(currencyIn:String, currencyOut: String, date: LocalDateTime):ExchangeRateRelation
}

