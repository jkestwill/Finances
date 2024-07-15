package com.jk.financehelper.domain.model

import com.jk.transaction.TransactionCategory

data class Operation(
    val id:String,
    val name:String,
    val money:TransactionMoney,
    val category:List<com.jk.transaction.TransactionCategory>,
    val schedule: Schedule?
) {
}