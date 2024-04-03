package com.jk.financehelper.domain.model

import javax.annotation.concurrent.Immutable

@Immutable
data class TransactionMoney(
    val id:String,
    val currency:Currency,
    val amount:Double
) {
}