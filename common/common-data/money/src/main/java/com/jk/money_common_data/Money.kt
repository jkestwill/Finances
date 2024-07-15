package com.jk.money_common_data

data class Money(
    val id:String,
    val amount:Double,
    val currency:Currency
)

data class Currency(
    val id:String,
    val name:String
)