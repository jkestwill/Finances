package com.jk.settings

fun Settings.toSettingsProto(): AppSettingsProto.Builder? {
    return AppSettingsProto.newBuilder()
        .setIsAnimeEnabled(isAnimeEnabled)
        .setLocale(locale.toProto())
        .setSecurity(security.toProto())
        .setCurrencyConfig(currencyConfig.toProto())
}


fun Locale.toProto():LocaleProto{
    return LocaleProto.newBuilder().setCountry(country).setLanguage(language).build()
}
fun Coordinates.toProto():CoordinatesProto{
    return CoordinatesProto.newBuilder().setLat(lat).setLang(long).build()
}
fun SecurityMethod.toProto():SecurityMethodProto{
    return SecurityMethodProto.valueOf(name)
}

fun Security.toProto():SecurityProto{
    return SecurityProto.newBuilder()
        .setSecurityMethod(securityMethod.toProto())
        .setPassword(password)
        .setCanScreenshot(canScreenshot)
        .setScreenAppMenuVisibility(screenAppsMenuVisibility)
        .build()
}

fun CurrencyConfig.toProto():CurrencyConfigProto{
    return CurrencyConfigProto.newBuilder().setBankConfig(bankConfig.toProto()).setDefaultCurrency(defaultCurrency).build()
}

fun BankConfig.toProto():BankConfigProto{
    return BankConfigProto.newBuilder()
        .setName(name)
        .setAddress(address)
        .setCoordinates(coordinates.toProto())
        .setAbbreviation(abbreviation)
        .setImageUrl(imageUrl)
        .build()
}
//  ################################### Proto to default ###########333###################################

fun CoordinatesProto.toCoordinates():Coordinates {
    return Coordinates(lat=lat,long = lang)
}

fun AppSettingsProto.toDefault():Settings{
    return Settings(isAnimeEnabled=isAnimeEnabled, locale = locale.toDefault(), currencyConfig =currencyConfig.toDefault(), security = security.toDefault())
}

fun LocaleProto.toDefault():Locale{
    return Locale(language=language, country = country)
}

fun SecurityProto.toDefault():Security{
    return Security(password=password,canScreenshot=canScreenshot,screenAppsMenuVisibility=screenAppMenuVisibility, securityMethod = SecurityMethod.valueOf(securityMethod.name))
}

fun CurrencyConfigProto.toDefault():CurrencyConfig{
    return CurrencyConfig(defaultCurrency=defaultCurrency,bankConfig=bankConfig.toDefault())
}

fun BankConfigProto.toDefault():BankConfig{
    return  BankConfig(name=name,abbreviation=abbreviation,address=address, coordinates = coordinates.toCoordinates(),imageUrl=imageUrl)
}
