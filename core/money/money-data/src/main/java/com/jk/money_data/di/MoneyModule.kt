package com.jk.money_data.di

import com.jk.money_data.CurrencyRepository
import com.jk.transaction_database.transaction.dao.BankDao
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoneyModule {

    @Singleton
    @Provides
    fun provideCurrencyDao(db: TransactionDatabaseProvider): CurrencyDao {
        return db.getCurrencyDao()
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(currencyDao: CurrencyDao): CurrencyRepository {
        return CurrencyRepository(currencyDao)
    }

    @Singleton
    @Provides
    fun provideMoneyDao(db: TransactionDatabaseProvider): MoneyDao {
        return db.getMoneyDao()
    }


}