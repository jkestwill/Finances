package com.jk.common_data

import android.location.Address
import java.util.Locale

data class AppSettings(
    val locale: Locale,
    val currencyConfig: CurrencyConfig,
    val security: Security
) {
}

data class CurrencyConfig(
    val defaultCurrencyName:String,
    val defaultCurrencyExchangeBank:BankConfig
)

data class BankConfig(
    val name:String,
    val abbreviation:String?,
    val address:String,
    val coordinates:Coordinates,
    val imageUrl:String?
)


data class Coordinates(
    val long:Short,
    val lat:Short
)

data class Security(
    val password:String,
    val loginMethod:LoginMethod,
    val canScreenshot:Boolean,
    // виден ли экран при выходе в меню открытых приложений
    val screenAppMenuVisibility:Boolean
){
    enum class LoginMethod{
        PASSWORD_NUMBER,PASSWORD_STRING,FINGERPRINT,NONE
    }
}