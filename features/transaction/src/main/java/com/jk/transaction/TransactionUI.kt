package com.jk.transaction

import com.jk.category.CategoryUI
import com.jk.currency.MoneyUI
import com.jk.goods.GoodsUI
import com.jk.transaction_data.TransactionType
import java.time.LocalDateTime

data class TransactionUI(
    val id:String,
    val operation:OperationUI,
    val date: LocalDateTime,
    val type: TransactionTypeUI
) {
}

data class TransactionTypeUI(
    val id:String,
    val name:String
)
data class OperationUI(
    val id:String,
    val name:String,
    val categoryList:List<CategoryUI>,
    val scheduleList:List<ScheduleUI>,
    val goodsList:List<GoodsUI>,
    val money: MoneyUI
)


data class ScheduleUI(
    val id:String,
    val dateStart: LocalDateTime,
    val countLeft:String,
    val repeatPeriodMillis:Long
)
