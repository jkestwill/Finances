package com.jk.money_common_ui

data class MoneyUI(
    val id:String,
    val amount:Double,
    val currency: CurrencyUI
)

data class CurrencyUI(
    val id:String,
    val name:String
)

