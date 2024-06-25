package com.jk.financehelper.utils
/**
 * Setting that will be saved in SharedPreference
 * @param isAnimeEnabled - on/off anime girl
 * @param mainCurrency - the currency that is displayed by default
 * */
data class AppConfig(
    val isAnimeEnabled:Boolean,
    val mainCurrency:String
) {
}