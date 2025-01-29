package com.jk.transaction_database.transaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.LocalTime

@Entity("schedule")
data class ScheduleEntity(
    @PrimaryKey
    val id:String,
    @ColumnInfo("start_date")
    val startDate: LocalDateTime,
    @ColumnInfo("count_left")
    val countLeft:Int,
    @ColumnInfo("time")
    val time:LocalTime?,
    @ColumnInfo("day")
    val day:Byte?,
    @ColumnInfo("week")
    val week:Byte?,
    @ColumnInfo("month")
    val month:Byte?,
    @ColumnInfo("period_millis")
    val periodMillis:Long?
) {
}