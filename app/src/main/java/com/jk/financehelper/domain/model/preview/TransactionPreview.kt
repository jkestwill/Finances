package com.jk.financehelper.domain.model.preview

import com.jk.financehelper.domain.model.TransactionType
import java.time.LocalDateTime
data class TransactionPreview(
    val id:String,
    val operation:OperationPreview,
    val date: LocalDateTime,
    val type:TransactionType
) {
}