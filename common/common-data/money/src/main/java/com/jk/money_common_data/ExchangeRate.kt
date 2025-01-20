package com.jk.money_common_data

import java.time.LocalDateTime

//scale - currencyIn scale for example 100 RUB == 3.2112
data class ExchangeRate(
    val currencyIn:String,
    val currencyOut:String,
    val scale:Int,
    val rate:Double,
    val date:LocalDateTime,
    val bank:Bank?
)