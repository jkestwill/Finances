package com.jk.transaction_common_data

import com.jk.category_common_data.Category
import com.jk.common_goods_data.GoodsPurchase
import com.jk.money_common_data.Money
import com.jk.money_common_data.MoneyAccount
import java.time.LocalDateTime
import java.time.LocalTime

data class Transaction(
    val id:String,
    val operation: Operation,
    val date:LocalDateTime,
    val type: TransactionType,
    val moneyAccount: MoneyAccount
)




data class Operation(
    val id:String,
    val name:String,
    val categoryList:List<Category>,
    val scheduleList:List<Schedule>,
    val goodsList:List<GoodsPurchase>,
    val money: Money,
    val isExpenses:Boolean,
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

