package com.jk.goods

data class SpecificationsUI(
    val id: String,
    val text:String,
    val measure: MeasureUI,
    val amount: Float
)
data class MeasureUI(
    val id: String,
    val language: LanguageUI
)

data class LanguageUI(
    val id: String,
    val text: String,
    val lanName: String,
    val lanShortName: String
)