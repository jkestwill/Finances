package com.jk.transaction_data

import java.time.LocalDateTime

data class TransactionPreview(
    val id:String,
    val operation:OperationPreview,
    val date: LocalDateTime,
    val type:String
)


data class OperationPreview(
    val id:String,
    val money:TransactionMoney,
    val name:String
)


data class TransactionMoney(
    val id:String,
    val currency:String,
    val amount:Double
)


