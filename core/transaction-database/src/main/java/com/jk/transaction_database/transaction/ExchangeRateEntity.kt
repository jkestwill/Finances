package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "exchange_rate",
    foreignKeys = [ForeignKey(
        entity = TransactionCurrencyDatabaseEntity::class,
        childColumns = ["currency_from_id"],
        parentColumns = ["id"]
    ),
        ForeignKey(
            entity = TransactionCurrencyDatabaseEntity::class,
            childColumns  = ["currency_to_id"],
            parentColumns = ["id"]
        )
    ]
)
data class ExchangeRateEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("currency_from_id")
    val currencyFromId: String,
    @ColumnInfo("currency_to_id")
    val currencyToId: String,
    val date: LocalDateTime,
    val rate: Double,
    val scale: Int
)