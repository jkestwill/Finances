package com.jk.transaction

import com.jk.category.toUI
import com.jk.currency.toUI
import com.jk.goods.toUI
import com.jk.transaction_data.Operation
import com.jk.transaction_data.Schedule
import com.jk.transaction_data.Transaction
import com.jk.transaction_data.TransactionType

fun Transaction.toUI(): TransactionUI {
    return TransactionUI(id=id,operation=operation.toUI(),date=date,type=type.toUI())
}


fun Operation.toUI(): OperationUI {
    return OperationUI(id = id,name=name,categoryList = categoryList.map { it.toUI() }, scheduleList = scheduleList.map { it.toUI() }, goodsList = goodsList.map { it.toUI()}, money = money.toUI())
}

fun Schedule.toUI():ScheduleUI{
    return ScheduleUI(id,dateStart, countLeft, repeatPeriodMillis)
}

fun TransactionType.toUI(): TransactionTypeUI {
    return TransactionTypeUI(id=id,name=name)
}