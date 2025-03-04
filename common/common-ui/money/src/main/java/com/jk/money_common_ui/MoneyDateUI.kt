package com.jk.money_common_ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MoneyDateUI(
    val id:String,
    val amount:Double,
    val currency: CurrencyUI,
    val date: LocalDate?
) : Parcelable
