package com.jk.financehelper.domain.model.preview

import com.jk.financehelper.domain.model.TransactionMoney

data class OperationPreview(
    val id:String,
    val money:TransactionMoney,
    val name:String
) {
}