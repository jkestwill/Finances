package com.jk.goods

import com.jk.currency.MoneyUI
import com.jk.money_data.Money

data class GoodsUI(
    val id:String,
    val name: String,
    val amount:Int,
    val specifications:List<SpecificationsUI>,
    val cost: MoneyUI
)

