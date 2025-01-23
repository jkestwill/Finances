package com.jk.exchange_rate_data.di

import com.example.currencyexchangeapi.ExchangeServiceFactory
import com.example.currencyexchangeapi.model.NBRBApi
import com.example.currencyexchangeapi.services.by.ExchangeServiceFactoryImpl
import com.jk.exchange_rate_data.BuildConfig
import com.jk.exchange_rate_data.CurrencyExchangeRepository
import com.jk.transaction_database.transaction.dao.BankDao
import com.jk.transaction_database.transaction.dao.ExchangeRateDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CurrencyExchangeModule {

    @Provides
    @Singleton
    fun provideExchangeServiceFactory(): ExchangeServiceFactory {
        return ExchangeServiceFactoryImpl()
    }

    @Provides
    @Singleton
    fun provideExchangeRepository(currencyExchangeFactory:ExchangeServiceFactory,exchangeRateDao: ExchangeRateDao,bankDao: BankDao): CurrencyExchangeRepository {
        return CurrencyExchangeRepository(currencyExchangeFactory,exchangeRateDao,bankDao)
    }
    @Provides
    @Singleton
    fun provideNBRBApi(httpClient: HttpClient): NBRBApi {
        return NBRBApi(baseUrl = BuildConfig.NBRB_API_BASE_URL, httpClient = httpClient)
    }
}