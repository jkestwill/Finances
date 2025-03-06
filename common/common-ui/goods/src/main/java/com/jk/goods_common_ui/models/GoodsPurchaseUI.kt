package com.jk.goods_common_ui.models

import com.jk.common_goods_data.Goods
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI

/**
 * Используется при создании транзакции для добавления товаров в ее список поэтому здесь есть кол-во.
 * Располагается на UI слое
 * */
data class GoodsPurchaseUI(
    val id: String,
    val name: String,
    val cost: MoneyUI,
    val amount: Int
) {

    class Builder() {
        var id: String = ""
        var name: String = ""
        var cost: MoneyUI = MoneyUI("", amount = 0.0, currency = CurrencyUI("", ""))
        var amount = 0

        constructor(goodsPurchaseUI: GoodsPurchaseUI) : this() {
            id = goodsPurchaseUI.id
            name = goodsPurchaseUI.name
            cost = goodsPurchaseUI.cost
            amount = goodsPurchaseUI.amount
        }

        fun id(id: String): Builder {
            this.id = id
            return this
        }

        fun name(name: String): Builder {
            this.name = name
            return this
        }
        fun amount(amount: Int):Builder{
            this.amount = amount
            return this
        }

        fun cost(cost: MoneyUI): Builder {
            this.cost = cost
            return this
        }

        fun build(): GoodsPurchaseUI {
            return GoodsPurchaseUI(id = id, name = name, cost = cost, amount = amount)
        }

        override fun hashCode(): Int {
            return id.hashCode() + name.hashCode() + cost.hashCode() + amount.hashCode()

        }

        override fun equals(other: Any?): Boolean {
            if (other == null) return false
            if (other is Builder) {
                return if (other.id.isEmpty())
                    other.name == name && other.cost == cost && other.amount == amount
                else other.id == id
            }
            if (other is Goods) {
                return other == build()
            }
            return false
        }

        override fun toString(): String {
            return build().toString()
        }
    }
}