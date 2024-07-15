package com.jk.transaction_common_ui

import com.jk.money_common_ui.MoneyUI
import java.time.LocalDateTime

data class TransactionPreviewUI(
    val id:String,
    val operation:OperationPreviewUI,
    val date: LocalDateTime,
    val type:String
)


data class OperationPreviewUI(
    val id:String,
    val money: MoneyUI,
    val name:String
)

