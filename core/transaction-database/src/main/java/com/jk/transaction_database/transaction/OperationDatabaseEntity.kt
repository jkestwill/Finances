package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "operation")
data class OperationDatabaseEntity(
    @PrimaryKey
    val id: String ,
    val name: String,
    @ColumnInfo("schedule_id")
    val scheduleId: String,
    @ColumnInfo("money_id")
    val moneyId:String
)