package com.jk.transaction_database.transaction.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.jk.transaction_database.transaction.ExchangeRateEntity
import com.jk.transaction_database.transaction.relations.ExchangeRateRelation
import java.time.LocalDateTime

@Dao
 interface ExchangeRateDao {
    @Insert(entity = ExchangeRateEntity::class)
    suspend fun insert(exchangeRateEntity: ExchangeRateEntity)

    @Transaction
    @Query(
        value = "" +
                "SELECT * FROM exchange_rate " +
                "INNER JOIN currency c_from ON c_from.id == exchange_rate.currency_from_id " +
                "INNER JOIN currency c_to ON c_to.id == exchange_rate.currency_to_id " +
                "ORDER BY exchange_rate.date DESC " +
                "LIMIT 1"
    )
    suspend fun getRelevantExchangeList(): ExchangeRateRelation?

    @Transaction
    @Query(
        "INSERT INTO exchange_rate VALUES(:id," +
                "CASE WHEN EXISTS (SELECT * FROM currency WHERE currency.name==:currencyNameIn) THEN (SELECT id FROM currency WHERE name==:currencyNameIn) " +
                "ELSE :currencyNameIn END ," +
                " CASE WHEN EXISTS (SELECT * FROM  currency WHERE currency.name==:currencyNameOut) THEN (SELECT id FROM currency WHERE name==:currencyNameIn) " +
                "ELSE :currencyNameOut END," +
                " :date," +
                " :rate," +
                ":scale)  "
    )
    suspend fun insert(
        id: String,
        currencyNameIn: String,
        currencyNameOut: String,
        date: LocalDateTime,
        scale: Int,
        rate: Double
    )

    @Query("SELECT * FROM exchange_rate WHERE exchange_rate.id LIKE :id")
    suspend fun getOrNull(id: String): ExchangeRateEntity?

}

