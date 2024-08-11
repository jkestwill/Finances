package com.jk.goods_common_ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecificationsUI(
    val id: String,
    val text: String,
    val measure: MeasureUI,
    val amount: Float
) : Parcelable

@Parcelize
data class MeasureUI(
    val id: String,
    val language: LanguageUI
) : Parcelable

@Parcelize
data class LanguageUI(
    val id: String,
    val text: String,
    val lanName: String,
    val lanShortName: String
) : Parcelable