package com.jk.exchange_rate_data

import com.example.currencyexchangeapi.ExchangeServiceFactory
import com.example.currencyexchangeapi.services.by.ExchangeServiceFactoryImpl
import com.jk.transaction_database.transaction.dao.ExchangeRateDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun provideExchangeRateDao(transactionDatabase: TransactionDatabaseProvider): ExchangeRateDao {
        return transactionDatabase.getExchangeRateDao()
    }
}