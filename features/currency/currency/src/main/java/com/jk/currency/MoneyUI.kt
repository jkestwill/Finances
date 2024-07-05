package com.jk.currency

data class MoneyUI(
    val id:String,
    val amount:Double,
    val currency:CurrencyUI
)

data class CurrencyUI(
    val id:String,
    val name:String
)