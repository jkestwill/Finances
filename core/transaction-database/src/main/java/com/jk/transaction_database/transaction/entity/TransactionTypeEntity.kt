package com.jk.transaction_database.transaction.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type")
data class TransactionTypeEntity(
    @PrimaryKey
    val id: String,
    val name: String
)