package com.jk.transaction_common_ui


import com.jk.category_common_ui.toUI
import com.jk.transaction_common_data.Operation
import com.jk.transaction_common_data.Schedule
import com.jk.transaction_common_data.Transaction
import com.jk.transaction_common_data.TransactionType
import com.jk.goods_common_ui.toUI


fun Transaction.toUI(): TransactionUI {
    return TransactionUI(id=id,operation=operation.toUI(),date=date,type=type.toUI())
}


fun Operation.toUI(): OperationUI {
    return OperationUI(id = id,name=name,categoryList = categoryList.map { it.toUI() }, scheduleList = scheduleList.map { it.toUI() }, goodsList = goodsList.map { it.toUI()}, money = money.toUI())
}

fun Schedule.toUI(): ScheduleUI {
    return ScheduleUI(id,dateStart, countLeft, repeatPeriodMillis)
}

fun TransactionType.toUI(): TransactionTypeUI {
    return TransactionTypeUI(id=id,name=name)
}