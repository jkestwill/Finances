package com.jk.goods_common_ui.models

import com.jk.money_common_ui.MoneyUI
/**
 * Используется при создании транзакции для добавления товаров в ее список поэтому здесь есть кол-во.
 * Располагается на UI слое
 * */
data class GoodsPurchaseUI(
    val id:String,
    val name:String,
    val cost:MoneyUI,
    val amount:Int
)