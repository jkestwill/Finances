package com.jk.transaction_database.transaction.di

import android.content.Context
import com.jk.transaction_database.BuildConfig
import com.jk.transaction_database.transaction.dao.BankDao
import com.jk.transaction_database.transaction.dao.ExchangeRateDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import com.jk.transaction_database.transaction.database.transactionDatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TransactionDatabaseModule {
    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): TransactionDatabaseProvider =
        transactionDatabaseProvider(context, BuildConfig.PREPOPULATE_DB_PATH)


    @Provides
    @Singleton
    fun provideExchangeRateDao(transactionDatabase: TransactionDatabaseProvider): ExchangeRateDao {
        return transactionDatabase.getExchangeRateDao()
    }

    @Provides
    @Singleton
    fun provideBankDao(transactionDatabase: TransactionDatabaseProvider): BankDao {
        return transactionDatabase.getBankDao()
    }
}