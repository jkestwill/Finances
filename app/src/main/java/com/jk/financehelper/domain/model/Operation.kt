package com.jk.financehelper.domain.model

import com.jk.category_data.TransactionCategory

data class Operation(
    val id:String,
    val name:String,
    val money:TransactionMoney,
    val category:List<TransactionCategory>,
    val schedule: Schedule?
) {
}