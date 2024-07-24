package com.jk.money_data

import com.jk.money_common_data.Currency

data class Money(
    val id:String,
    val amount:Double,
    val currency: Currency
)

