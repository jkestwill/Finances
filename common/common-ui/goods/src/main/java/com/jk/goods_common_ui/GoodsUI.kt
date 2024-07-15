package com.jk.goods_common_ui

import com.jk.money_common_ui.MoneyUI


data class GoodsUI(
    val id:String,
    val name: String,
    val amount:Int,
    val specifications:List<SpecificationsUI>,
    val cost: MoneyUI
)

