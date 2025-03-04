package com.jk.transaction_database.transaction.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "store")
data class StoreEntity(
    @PrimaryKey
    val id:String,
    val name:String
)