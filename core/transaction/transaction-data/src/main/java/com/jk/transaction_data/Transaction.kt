package com.jk.transaction_data

import com.jk.category_data.TransactionCategory
import com.jk.goods.Goods
import java.time.LocalDateTime

data class Transaction(
    val id:String,
    val operation:Operation,
    val date:LocalDateTime,
    val type:TransactionType
) {
}

data class Operation(
    val id:String,
    val name:String,
    val categoryList:List<TransactionCategory>,
    val scheduleList:List<Schedule>,
    val goodsList:List<Goods>,
    val money:Money
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
    val currency:String
)

