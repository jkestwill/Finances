package com.jk.financehelper.domain.model

import com.jk.transaction_common_data.Transaction
import java.time.LocalDate

data class Ledger(
    val name: String,
    val transactions: List<Transaction>,
    val dateCreated: LocalDate
) {
}