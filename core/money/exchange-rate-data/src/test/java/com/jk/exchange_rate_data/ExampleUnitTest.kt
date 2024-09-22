package com.jk.exchange_rate_data

import androidx.test.platform.app.InstrumentationRegistry

import com.example.currencyexchangeapi.ExchangeRateRequestParams
import com.example.currencyexchangeapi.ExchangeRateServices
import com.example.currencyexchangeapi.services.by.ExchangeServiceFactoryImpl
import com.jk.transaction_database.transaction.database.transactionDatabase
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import  androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)

class ExchangeRateTest{
    val transactionDatabase = transactionDatabase(InstrumentationRegistry.getInstrumentation().context,"")
    val currencyExchangeFactory = ExchangeServiceFactoryImpl()
    val exchangeRateDao = transactionDatabase.getExchangeRateDao()
    val bankDao = transactionDatabase.getBankDao()
    val currencyExchangeRepository = CurrencyExchangeRepository(currencyExchangeFactory,exchangeRateDao,bankDao)

    @Test
    fun fetch(){
       val result =  currencyExchangeRepository.getExchangeRate(
            ExchangeRateRequestParams(currencyIn = "BYN", currencyOut = "USD", date = LocalDateTime.now()),
            exchangeRateServices = ExchangeRateServices.NBRB("https://api.nbrb.by/exrates"),
            mergeStrategy = ApiRequestMergeStrategy()
        )

        assertEquals(result,result)
    }
}
