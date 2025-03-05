package com.jk.money_common_ui

import com.jk.money_common_data.Currency
import org.mapstruct.Mapper

@Mapper
interface CurrencyUIMapper {

    fun toUI(currency: Currency):CurrencyUI

    fun toCurrency(currencyUI: CurrencyUI):Currency
}