package com.jk.financehelper.di

import com.jk.money_data.CurrencyRepository
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CurrencyModule {

    @Singleton
    @Provides
    fun provideCurrencyDao(db: TransactionDatabase): CurrencyDao {
        return db.getCurrencyDao()
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(currencyDao: CurrencyDao): CurrencyRepository {
        return CurrencyRepository(currencyDao)
    }

}