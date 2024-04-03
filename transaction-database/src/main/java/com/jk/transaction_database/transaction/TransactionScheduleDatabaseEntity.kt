package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
@Entity("schedule")
data class TransactionScheduleDatabaseEntity(
    @PrimaryKey
    val id:String,
    @ColumnInfo("start_date")
    val startDate: LocalDateTime,
    @ColumnInfo("count_left")
    val countLeft:Int,
    @ColumnInfo("period_millis")
    val periodMillis:Long
) {
}