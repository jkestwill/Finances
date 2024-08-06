package com.jk.money_common_ui

import com.jk.common_data.Selectable

data class MoneyUI(
    val id:String,
    val amount:Double,
    val currency: CurrencyUI
)

data class CurrencyUI(
    val id:String,
    val name:String
): Selectable{
    override val value: String
        get() = name
}

