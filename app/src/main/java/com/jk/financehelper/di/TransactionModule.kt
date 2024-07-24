package com.jk.financehelper.di

import com.jk.transaction_data.TransactionPagingSourceFactory
import com.jk.transaction_data.TransactionRepository
import com.jk.transaction_data.datasource.TransactionPagingLocalSource
import com.jk.transaction_data.datasource.TransactionRemoteDataSource
import com.jk.transaction_database.transaction.dao.TransactionDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TransactionModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(): TransactionRemoteDataSource {
        return TransactionRemoteDataSource()
    }
    @Provides
    @Singleton
    fun provideDao(db:TransactionDatabase):TransactionDao{
        return db.getTransactionDao()
    }

@Provides
@Singleton
    fun provideTransactionRepository(
        db:TransactionDatabase,
        transactionPagingSource:TransactionPagingSourceFactory
    ): TransactionRepository {
        return TransactionRepository(transactionDao = db.getTransactionDao(), currencyDao = db.getCurrencyDao(), transactionPagingSource = transactionPagingSource)
    }

}