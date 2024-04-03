package com.jk.financehelper.domain.model

import javax.annotation.concurrent.Immutable

@Immutable
data class Goods(
    val id:String,
    val name:String,
    val money:TransactionMoney
) {
}