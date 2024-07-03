package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "operation")
data class OperationEntity(
    @PrimaryKey
    val id: String ,
    val name: String,
    @ColumnInfo("money_id")
    val moneyId:String
)