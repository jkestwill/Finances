package com.jk.transaction_database.transaction.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "currency", indices = [Index("name", unique = true)])
data class CurrencyEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo("name")
    val name: String
)
