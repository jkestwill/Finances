package com.jk.financehelper.utils

import com.jk.category_common_ui.CategoryUI
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import com.jk.transaction_common_ui.MoneyAccountUI
import com.jk.transaction_common_ui.OperationUI
import com.jk.transaction_common_ui.TransactionTypeUI
import com.jk.transaction_common_ui.TransactionUI
import java.time.LocalDateTime

class DataUtils {

    companion object {
        fun getBarChartData() = listOf<TransactionUI>(
            TransactionUI(
                id = "qq",
                operation = OperationUI(
                    id = "zxc",
                    name = "qq",
                    money = MoneyUI(
                        id = "zxc2",
                        amount = 200.0,
                        currency = CurrencyUI(id = "qwe", name = "BYN")
                    ),
                    categoryList = listOf(
                        CategoryUI(
                            id = "x1",
                            "Sport",
                            color = (0xFF0094C6).toInt()
                        )
                    ),
                    scheduleList = listOf(),
                    goodsList = listOf()
                ),
                date = LocalDateTime.now(),
                type = TransactionTypeUI(id = "ww", "online"),
                moneyAccountUI = MoneyAccountUI("","", moneyUI = MoneyUI("",20.0,CurrencyUI("","BYN")))
            ),
            TransactionUI(
                id = "qq",
                operation = OperationUI(
                    id = "zxc",
                    name = "qq",
                    money = MoneyUI(
                        id = "zxc2",
                        amount = 200.0,
                        currency = CurrencyUI(id = "qwe", name = "BYN")
                    ),
                    categoryList = listOf(
                        CategoryUI(
                            id = "x1",
                            "Sport",
                            color =  (0xFF0094C6).toInt()
                        )
                    ),
                    scheduleList = listOf(),
                    goodsList = listOf()
                ),
                date = LocalDateTime.now(),
                type = TransactionTypeUI(id = "ww", "online"),
                moneyAccountUI = MoneyAccountUI("","", moneyUI = MoneyUI("",20.0,CurrencyUI("","BYN")))
            ),
        )


    }
}