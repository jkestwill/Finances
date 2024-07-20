package com.jk.transaction_common_ui

import com.jk.category_common_ui.CategoryUI
import com.jk.goods_common_ui.GoodsUI
import com.jk.money_common_ui.MoneyUI
import java.time.LocalDateTime

data class TransactionUI(
    val id:String,
    val operation: OperationUI,
    val date: LocalDateTime,
    val type: TransactionTypeUI
)

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
    val countLeft:Int,
    val repeatPeriodMillis:Long
)
