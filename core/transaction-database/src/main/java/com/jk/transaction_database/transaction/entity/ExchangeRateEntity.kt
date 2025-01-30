package com.jk.transaction_database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "exchange_rate",
    foreignKeys = [
        ForeignKey(
            entity = CurrencyEntity::class,
            childColumns = ["currency_from_id"],
            parentColumns = ["id"]
        ),
        ForeignKey(
            entity = CurrencyEntity::class,
            childColumns  = ["currency_to_id"],
            parentColumns = ["id"]
        ),
        ForeignKey(
            entity = BankEntity::class,
            childColumns  = ["bank_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
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
    @ColumnInfo("date")
    val date: LocalDateTime,
    @ColumnInfo("rate")
    val rate: Double,
    @ColumnInfo("scale")
    val scale: Int,
    @ColumnInfo("bank_id")
    val bankId:String
)