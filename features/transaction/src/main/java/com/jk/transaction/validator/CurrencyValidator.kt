package com.jk.transaction.validator

import com.jk.common_data.CurrencyConstraints
import com.jk.common_data.FinanceHelperException
import com.jk.common_data.Validator
import com.jk.common_data.exceptions.CurrencyNameLengthException
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


