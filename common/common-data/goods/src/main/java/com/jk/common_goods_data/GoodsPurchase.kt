package com.jk.common_goods_data

import com.jk.money_common_data.Money

data class GoodsPurchase(
    val id:String,
    val name:String,
    val cost:Money,
    val amount:Int,
    val specificationsList:List<Specification>?
)