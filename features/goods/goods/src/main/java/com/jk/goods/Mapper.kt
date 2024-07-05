package com.jk.goods

import com.jk.currency.toUI

fun Goods.toUI(): GoodsUI {
    return GoodsUI(
        id, name, specifications = specifications.map { it.toUI() }, cost = cost.toUI()
    )
}

fun Specification.toUI(): SpecificationsUI {
    return SpecificationsUI(id, text, measure = measure.toUI(), amount = amount)
}

fun Measure.toUI(): MeasureUI {
    return MeasureUI(
        id = id, language = language.toUI()
    )
}

fun Language.toUI(): LanguageUI {
    return LanguageUI(
        id, text, lanName, lanShortName
    )
}