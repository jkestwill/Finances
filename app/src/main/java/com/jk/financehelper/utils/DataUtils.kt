package com.jk.financehelper.utils

import com.jk.transaction.TransactionCategory
import com.jk.financehelper.domain.model.Currency
import com.jk.financehelper.domain.model.Operation
import com.jk.financehelper.domain.model.Transaction
import com.jk.financehelper.domain.model.TransactionMoney
import com.jk.financehelper.domain.model.TransactionType
import com.jk.financehelper.domain.model.preview.OperationPreview
import com.jk.financehelper.domain.model.preview.TransactionPreview
import java.time.LocalDateTime

class DataUtils {

    companion object {
        fun getBarChartData() = listOf<Transaction>(
            Transaction(
                id = "qq",
                operation = Operation(
                    id = "zxc",
                    name = "qq",
                    money = TransactionMoney(
                        id = "zxc2",
                        amount = 200.0,
                        currency = Currency(id = "qwe", name = "BYN")
                    ),
                    category = listOf(
                        com.jk.transaction.TransactionCategory(
                            id = "x1",
                            "Sport",
                            color = 0xFF0094C6U,
                            true
                        )
                    ),
                    schedule = null
                ),
                date = LocalDateTime.now(),
                goods = null,
                type = TransactionType(id = "ww", "online")
            ),
            Transaction(
                id = "qq2",
                operation = Operation(
                    id = "zxc13",
                    name = "qqa",
                    money = TransactionMoney(
                        id = "zxc3",
                        amount = 300.0,
                        currency = Currency(id = "qwe", name = "BYN")
                    ),
                    category = listOf(
                        com.jk.transaction.TransactionCategory(
                            id = "xx",
                            "Sport",
                            0xFF0094C6U,
                            false
                        )
                    ),
                    schedule = null
                ),
                date = LocalDateTime.of(2021, 2, 1, 3, 2),
                goods = null,
                type = TransactionType(id = "ww", "online")
            )
        )

        fun getTransactionPreview(): List<TransactionPreview> {
            return listOf<TransactionPreview>(
                TransactionPreview(
                    "t1",
                    operation = OperationPreview(
                        "1",
                        TransactionMoney(
                            "m1",
                            amount = 123.0,
                            currency = Currency("1", "BYN")
                        ),
                        name = "Euroopt LCC WAIKIKI PYATSOT DENEG"
                    ),
                    date = LocalDateTime.now(),
                    type = TransactionType("tp1", "online")
                ),
                TransactionPreview(
                    "t2",
                    operation = OperationPreview(
                        "2",
                        TransactionMoney(
                            "m2",
                            amount = 1993.0,
                            currency = Currency("1", "BYN")
                        ),
                        name = "Dionis"
                    ),
                    date = LocalDateTime.of(2023,1,2,13,24),
                    type = TransactionType("tp1", "online")
                )
            )
        }
    }
}