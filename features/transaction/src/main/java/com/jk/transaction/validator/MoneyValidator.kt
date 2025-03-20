package com.jk.transaction.validator

import com.jk.common_data.Validator
import com.jk.money_common_ui.exceptions.MoneyConstraints
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI
import javax.inject.Inject

class MoneyValidator @Inject constructor(
    private val currencyValidator: Validator<CurrencyUI>
) : Validator<MoneyUI> {
    override fun validate(target: MoneyUI) {
        currencyValidator.validate(target.currency)
        when {
            target.amount < MoneyConstraints.AMOUNT_MIN -> {
                throw IllegalArgumentException("Money amount can't be below zero")
            }
        }
    }
}