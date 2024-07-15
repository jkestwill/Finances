package com.jk.common_goods_data

import com.jk.money_common_data.Money


data class Goods(
    val id: String,
    val name: String,
    val amount:Int,
    val specifications:List<Specification>,
    val cost: Money
) {
}
/**
 * Характеристики товара.
 * @param text - название
 * @param measure - единица измерения. Например кг, гр, мл и тд.
 * @param amount - количество хаорактеристики
 * */
data class Specification(
    val id: String,
    val text:String,
    val measure: Measure,
    val amount: Float
)
/**
 * Ед измерения*/
data class Measure(
    val id: String,
    val language: Language
)
/**
 * Вариации текста на разных языках
 * */
data class Language(
    val id: String,
    val text: String,
    val lanName: String,
    val lanShortName: String
)