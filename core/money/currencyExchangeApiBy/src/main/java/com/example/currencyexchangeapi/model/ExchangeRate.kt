package com.example.currencyexchangeapi.model

 data class ExchangeRate(
     val currencyAbbreviation:String,
     val scale:Int,
     val rateSell:Double,
     val rateBuy:Double
 )