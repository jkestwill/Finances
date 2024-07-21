package com.jk.transaction_common_ui


import com.jk.category_common_ui.toCategory
import com.jk.category_common_ui.toUI
import com.jk.goods_common_ui.toGoods
import com.jk.goods_common_ui.toUI
import com.jk.money_common_ui.toMoney
import com.jk.money_common_ui.toUI
import com.jk.transaction_common_data.Operation
import com.jk.transaction_common_data.OperationPreview
import com.jk.transaction_common_data.Schedule
import com.jk.transaction_common_data.Transaction
import com.jk.transaction_common_data.TransactionPreview
import com.jk.transaction_common_data.TransactionType


fun Transaction.toUI(): TransactionUI {
    return TransactionUI(id = id, operation = operation.toUI(), date = date, type = type.toUI())
}


fun Operation.toUI(): OperationUI {
    return OperationUI(
        id = id,
        name = name,
        categoryList = categoryList.map { it.toUI() },
        scheduleList = scheduleList.map { it.toUI() },
        goodsList = goodsList.map { it.toUI() },
        money = money.toUI()
    )
}

fun Schedule.toUI(): ScheduleUI {
    return ScheduleUI(id, dateStart, countLeft, repeatPeriodMillis)
}

fun TransactionType.toUI(): TransactionTypeUI {
    return TransactionTypeUI(id = id, name = name)
}

fun TransactionPreview.toUI(): TransactionPreviewUI {
    return TransactionPreviewUI(id, operation = operation.toUI(), date = date, type = type)
}

fun OperationPreview.toUI(): OperationPreviewUI {
    return OperationPreviewUI(id = id, money = money.toUI(), name = name)
}

fun TransactionUI.toTransaction(): Transaction {
    return Transaction(id, operation.toOperation(), date, type.toType())
}

fun TransactionTypeUI.toType(): TransactionType {
    return TransactionType(id, name)
}

fun OperationUI.toOperation(): Operation {
    return Operation(
        id,
        name,
        categoryList.map { it.toCategory() },
        scheduleList.map { it.toSchedule() },
        goodsList.map { it.toGoods() },
        money.toMoney()
    )
}

fun ScheduleUI.toSchedule(): Schedule {
    return Schedule(id, dateStart, countLeft, repeatPeriodMillis)
}