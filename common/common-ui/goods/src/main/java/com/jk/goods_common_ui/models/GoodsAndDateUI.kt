package com.jk.goods_common_ui.models

import com.jk.common_data.Selectable
import com.jk.common_goods_data.MoneyAndDate
import com.jk.goods_common_ui.SpecificationsUI
import com.jk.money_common_ui.MoneyDateUI
import com.jk.money_common_ui.MoneyUI

data class GoodsAndDateUI(
    val id: String,
    val name: String,
    val specifications: List<SpecificationsUI>,
    val moneyUI: MoneyDateUI
) : Selectable {
    override val value: String
        get() = id
}