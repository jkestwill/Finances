package com.jk.settings

class Settings (
    val isAnimeEnabled:Boolean,
    val locale:Locale,
    val currencyConfig:CurrencyConfig,
    val security:Security,
)

data class Locale(
    val country:String,
    val language:String
)

data class CurrencyConfig(
    val defaultCurrency:String,
    val bankConfig:BankConfig
)

data class BankConfig(
    val name:String,
    val abbreviation:String,
    val address:String,
    val coordinates:Coordinates,
    val imageUrl:String?
)


data class Coordinates(
    val lat:Float,
    val long:Float
)

data class Security(
    val password:String,
    val canScreenshot:Boolean,
    val screenAppsMenuVisibility:Boolean,
    val securityMethod:SecurityMethod
)

enum class SecurityMethod{
    PASSWORD,
    PASSWORD_STRING,
    FINGERPRINT,
    NONE
}

