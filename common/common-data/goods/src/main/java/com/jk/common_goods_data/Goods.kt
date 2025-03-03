package com.jk.common_goods_data

import com.jk.money_common_data.Currency
import java.time.LocalDate


data class Goods(
    val id: String,
    val name: String,
    val amount:Int,
    val specifications:List<Specification>,
    val cost: List<MoneyAndDate>
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
    val name: String
)
/**
 * Вариации текста на разных языках
 * */
data class Language(
    val id: String,
    val lanName: String,
    val lanShortName: String
)
/**
 * Цена привязанная к дате. Нужна для отслеживания динамики цен
 **/
data class MoneyAndDate(
    val id:String,
    val amount:Double,
    val currency: Currency,
    val date:LocalDate
)