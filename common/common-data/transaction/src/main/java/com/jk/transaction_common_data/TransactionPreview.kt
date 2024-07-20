package com.jk.transaction_common_data

import com.jk.money_common_data.Money
import java.time.LocalDateTime

data class TransactionPreview(
    val id:String,
    val operation: OperationPreview,
    val date: LocalDateTime,
    val type:String
)


data class OperationPreview(
    val id:String,
    val money: Money,
    val name:String
)



