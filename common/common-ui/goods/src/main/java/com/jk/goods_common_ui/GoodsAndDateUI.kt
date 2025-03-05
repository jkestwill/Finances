package com.jk.goods_common_ui

import com.jk.common_data.Selectable
import com.jk.money_common_ui.MoneyUI

data class GoodsAndDateUI(
    val id: String,
    val name: String,
    val specifications: List<SpecificationsUI>,
    val moneyUI: MoneyUI
) : Selectable {
    override val value: String
        get() = id
}