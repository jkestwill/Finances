package com.jk.transaction.validator

import com.jk.money_common_ui.exceptions.CurrencyConstraints
import com.jk.common_data.Validator
import com.jk.money_common_ui.exceptions.CurrencyNameLengthException
import com.jk.money_common_ui.CurrencyUI

class CurrencyValidator:Validator<CurrencyUI> {
    companion object{

    }

    override fun validate(target: CurrencyUI) {
        when{
            target.name.length > CurrencyConstraints.MIN_CURRENCY_NAME_LENGTH->{
                //"Currency code's length can't be longer than 3"
                throw CurrencyNameLengthException()
            }
        }
    }
}


