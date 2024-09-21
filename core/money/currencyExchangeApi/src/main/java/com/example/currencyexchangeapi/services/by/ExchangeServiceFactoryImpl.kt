package com.example.currencyexchangeapi.services.by

import com.example.currencyexchangeapi.Bank
import com.example.currencyexchangeapi.ExchangeRateService
import com.example.currencyexchangeapi.ExchangeRateServices
import com.example.currencyexchangeapi.ExchangeServiceFactory
import com.example.currencyexchangeapi.model.NBRBApi
import io.ktor.client.HttpClient

class ExchangeServiceFactoryImpl(private val httpClient: HttpClient): ExchangeServiceFactory {
    override fun create(exchangeService: ExchangeRateServices): ExchangeRateService {
        return when(exchangeService){
            is ExchangeRateServices.NBRB -> {
                NBRBApi(httpClient = httpClient, baseUrl = exchangeService.baseUrl)
            }

            else -> {
                throw IllegalArgumentException("Wrong argument ${exchangeService}")
            }

        }
    }

    fun getBankList():List<Bank>{
        return listOf()
    }
}

