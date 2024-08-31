package com.jk.transaction_common_data

import com.jk.category_common_data.TransactionCategory
import com.jk.common_goods_data.Goods
import com.jk.money_common_data.Money
import java.time.LocalDateTime
import java.time.LocalTime

data class Transaction(
    val id:String,
    val operation: Operation,
    val date:LocalDateTime,
    val type: TransactionType
) {
}

data class Operation(
    val id:String,
    val name:String,
    val categoryList:List<TransactionCategory>,
    val scheduleList:List<Schedule>,
    val goodsList:List<Goods>,
    val money: Money
)

data class Schedule(
    val id:String,
    val dateStart:LocalDateTime,
    val countLeft:Int,
    val repeatPeriodMillis:Long?,
    val time:LocalTime?,
    val day:Byte?,
    val week:Byte?,
    val month:Byte?
)

