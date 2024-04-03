package com.example.currencyexchangeapi

import com.example.currencyexchangeapi.model.BodyType
import com.example.currencyexchangeapi.services.by.NBRBApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.time.Duration

fun main(): Unit = runBlocking{
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .connectTimeout(Duration.ofSeconds(1000))
        .callTimeout(Duration.ofSeconds(1000))
        .writeTimeout(Duration.ofSeconds(1000))
        .build()
    val nbrbApi = Api<NBRBApi>(BankApiUrl.NBRB, okHttpClient, BodyType.JSON)

    println(nbrbApi.exchange("RUB", NBRBApi.PARAM_MODE_2))


}