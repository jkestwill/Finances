package com.example.currencyexchangeapi

sealed class ExchangeCurrencies(
    val apiUrlList: List<Bank>
) {
    data object BYN : ExchangeCurrencies(
        listOf(Bank(name = "Belarusbank", url = BankApiUrl.BELARUSBANK), Bank(name="NBRB",url=BankApiUrl.NBRB,))
    )

    data object USD :
        ExchangeCurrencies(listOf())

    data object CNY : ExchangeCurrencies(listOf())


}

object BankApiUrl {
    const val BELARUSBANK = "https://belarusbank.by/api/"
    const val NBRB = "https://api.nbrb.by/exrates/"
    const val BELAGROPROMBANK = "https://belapb.by/"
}

data class Bank(
    val name: String,
    val url: String
)