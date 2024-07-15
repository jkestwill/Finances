package com.jk.goods

import com.jk.currency.toUI

fun com.jk.common_goods_data.Goods.toUI(): com.jk.transaction_common_ui.GoodsUI {
    return GoodsUI(
        id, name, specifications = specifications.map { it.toUI() }, cost = cost.toUI(), amount = amount
    )
}

fun com.jk.common_goods_data.Specification.toUI(): com.jk.transaction_common_ui.SpecificationsUI {
    return SpecificationsUI(id, text, measure = measure.toUI(), amount = amount)
}

fun com.jk.common_goods_data.Measure.toUI(): com.jk.transaction_common_ui.MeasureUI {
    return MeasureUI(
        id = id, language = language.toUI()
    )
}

fun com.jk.common_goods_data.Language.toUI(): com.jk.transaction_common_ui.LanguageUI {
    return LanguageUI(
        id, text, lanName, lanShortName
    )
}