package com.example.currencyexchangeapi

data class Bank(
    val id:String,
    val name: String,
    val url: String,
    val abbreviation:String?,
    val address:Address?,
    val imageUrl:String?
)


data class Address(
    val id:String,
    val long:Short,
    val lat:Short,
    val address:String?
)