package com.jk.transaction_data

import java.time.LocalDateTime

data class Transaction(
    val id:String,
    val operation:Operation
) {
}

data class Operation(
    val id:String,
    val name:String,
    val scheduleList:List<Schedule>,
    val money:String
)

data class Schedule(
    val id:String,
    val dateStart:LocalDateTime,
    val countLeft:String,
    val repeatPeriodMillis:Long
)

data class Money(
    val id:String,
    val amount:Double,
    val
)

