package com.jk.financehelper.domain.model

import java.time.LocalDateTime
import javax.annotation.concurrent.Immutable

@Immutable
data class Transaction(
    val id:String,
    val date:LocalDateTime,
    val type:TransactionType,
    val operation: Operation,
    val goods:List<Goods>?
) {
}