package com.jk.goods_common_ui

import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI


data class GoodsUI(
    val id: String,
    val name: String,
    val amount: Int,
    val specifications: List<SpecificationsUI>,
    val cost: MoneyUI
) {
    class Builder() {

        private val id: String = ""
        private val name: String = ""
        private val amount = 0
        private val specifications = listOf<SpecificationsUI>()
        private val cost =
            MoneyUI(id = "", amount = 0.0, currency = CurrencyUI(id = "", name = ""))

        fun build(): GoodsUI {
            return GoodsUI(id, name, amount = amount, specifications, cost = cost)
        }
    }
}

