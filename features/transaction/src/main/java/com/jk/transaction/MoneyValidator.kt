package com.jk.transaction

import com.jk.common_data.Validator
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import javax.inject.Inject

class MoneyValidator @Inject constructor(
    private val currencyValidator: Validator<CurrencyUI>
) : Validator<MoneyUI> {
    override fun validate(target: MoneyUI) {
        when {
            target.amount < 0 -> {
                throw IllegalArgumentException("Money amount can't be below zero")
            }

            target.currency.name.isEmpty() -> {
                // provide default value in config
                throw IllegalArgumentException("Currency name can't be empty")
            }
        }
    }
}