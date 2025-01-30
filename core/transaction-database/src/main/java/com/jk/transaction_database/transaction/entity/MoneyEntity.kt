package com.jk.transaction_database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "money",
    foreignKeys = [ForeignKey(
        entity = CurrencyEntity::class,
        parentColumns = ["id"],
        childColumns = ["currency_id"]
    )]
)
data class MoneyEntity(
    @PrimaryKey
    val id: String,
    val amount: Double,
    @ColumnInfo("currency_id")
    val currencyId: String
)