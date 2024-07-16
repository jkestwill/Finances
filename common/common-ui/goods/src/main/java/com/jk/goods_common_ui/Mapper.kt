package com.jk.goods_common_ui

import com.jk.common_goods_data.Goods
import com.jk.common_goods_data.Language
import com.jk.common_goods_data.Measure
import com.jk.common_goods_data.Specification
import com.jk.money_common_data.Currency
import com.jk.money_common_data.Money
import com.jk.money_common_ui.CurrencyUI
import com.jk.money_common_ui.MoneyUI


fun Goods.toUI(): GoodsUI {
    return GoodsUI(
        id,
        name,
        specifications = specifications.map { it.toUI() },
        cost = cost.toUI(),
        amount = amount
    )
}

fun Money.toUI(): MoneyUI {
    return MoneyUI(
        id = id, amount = amount, currency = currency.toUI()
    )
}

fun Currency.toUI(): CurrencyUI {
    return CurrencyUI(id = id, name = name)
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
        id, text,lanName, lanShortName
    )
}

fun GoodsUI.toGoods(): Goods {
    return Goods(id=id,name=name,amount=amount,specifications=specifications.map { it.toSpecification() },cost=cost.toMoney())
}

fun SpecificationsUI.toSpecification(): Specification {
    return Specification(id=id,text=text,measure=measure.toMeasure(),amount=amount)
}

fun MeasureUI.toMeasure():Measure{
    return Measure(id=id,language.toLanguage())
}

fun LanguageUI.toLanguage(): Language {
    return Language(id, text, lanName, lanShortName)
}

fun MoneyUI.toMoney(): Money {
    return Money(id=id,amount=amount,currency=Currency(currency.id,currency.name))
}