package com.jk.transaction_common_ui

import com.jk.money_common_data.Currencies
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI

data class MoneyAccountUI(
    val id: String,
    val name: String,
    val moneyUI: MoneyUI
) {
    class Builder() {
        private var id: String = ""
        private var name: String = ""
        private var moneyUI =
            MoneyUI(id = "", amount = 0.0, currency = CurrencyUI("", Currencies.BYN.name))

        constructor(moneyAccount: MoneyAccountUI) : this() {
            id = moneyAccount.id
            name = moneyAccount.name
            moneyUI = moneyAccount.moneyUI
        }

        fun id(id: String): Builder {
            this.id = id
            return this
        }

        fun name(name: String): Builder {
            this.name = name
            return this
        }

        fun moneyUI(moneyUI: MoneyUI): Builder {
            this.moneyUI = moneyUI
            return this
        }

        fun build() = MoneyAccountUI(id = id, name = name, moneyUI = moneyUI)

        override fun equals(other: Any?): Boolean {
            return if (other is Builder) {
                other.id == id && other.moneyUI == moneyUI && other.name == name
            } else false
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }
}