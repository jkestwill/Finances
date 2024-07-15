package com.jk.transaction

import com.jk.category.toUI
import com.jk.currency.toUI
import com.jk.goods.toUI
import com.jk.`transaction-common-data`.Operation
import com.jk.`transaction-common-data`.Schedule
import com.jk.`transaction-common-data`.Transaction
import com.jk.`transaction-common-data`.TransactionType

fun com.jk.`transaction-common-data`.Transaction.toUI(): com.jk.transaction_common_ui.TransactionUI {
    return TransactionUI(id=id,operation=operation.toUI(),date=date,type=type.toUI())
}


fun com.jk.`transaction-common-data`.Operation.toUI(): com.jk.transaction_common_ui.OperationUI {
    return OperationUI(id = id,name=name,categoryList = categoryList.map { it.toUI() }, scheduleList = scheduleList.map { it.toUI() }, goodsList = goodsList.map { it.toUI()}, money = money.toUI())
}

fun com.jk.`transaction-common-data`.Schedule.toUI(): com.jk.transaction_common_ui.ScheduleUI {
    return ScheduleUI(id,dateStart, countLeft, repeatPeriodMillis)
}

fun com.jk.`transaction-common-data`.TransactionType.toUI(): com.jk.transaction_common_ui.TransactionTypeUI {
    return TransactionTypeUI(id=id,name=name)
}