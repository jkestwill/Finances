package com.jk.transaction

import com.jk.money_common_ui.MoneyUI

//товары с кол-вом т.е  факт покупки
data class GoodsPurchaseUI(
    val id:String,
    val name:String,
    val amount:String,
    val moneyUI:MoneyUI
)