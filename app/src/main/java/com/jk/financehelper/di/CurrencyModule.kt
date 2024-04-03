package com.jk.financehelper.di

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

}