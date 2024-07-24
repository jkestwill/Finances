package com.jk.transaction

import com.jk.money_common_data.Currency
import com.jk.money_common_ui.CurrencyUI

fun Currency.toUI(): CurrencyUI {
    return CurrencyUI(id,name)
}