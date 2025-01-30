package com.jk.transaction

import com.jk.common_data.Validator
import com.jk.money_common_ui.CurrencyUI

class CurrencyValidator:Validator<CurrencyUI> {
    override fun validate(target: CurrencyUI) {
        when{
            target.name.length > 3->{
                throw IllegalArgumentException("Currency code's length can't be longer than 3")
            }
        }
    }
}