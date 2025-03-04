package com.jk.money_common_ui

import android.os.Parcelable
import com.jk.common_data.Selectable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoneyUI(
    val id:String,
    val amount:Double,
    val currency: CurrencyUI,
):Parcelable

@Parcelize
data class CurrencyUI(
    val id:String,
    val name:String
): Selectable,Parcelable{
    override val value: String
        get() = name
}

