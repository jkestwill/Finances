package com.jk.transaction_database.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey
    val id: String,
    val name: String
)
