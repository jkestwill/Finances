package com.jk.transaction_data.di

import com.jk.transaction_data.TransactionPagingSourceFactory
import com.jk.transaction_data.TransactionRepository
import com.jk.transaction_data.datasource.TransactionRemoteDataSource
import com.jk.transaction_data.mapper.TransactionMapperImpl
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.OperationCategoryDao
import com.jk.transaction_database.transaction.dao.OperationDao
import com.jk.transaction_database.transaction.dao.TransactionDao
import com.jk.transaction_database.transaction.dao.TransactionGoodsListDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
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
    fun provideTransactionRepository(transactionDao: TransactionDao, currencyDao: CurrencyDao, transactionPagingSourceFactory: TransactionPagingSourceFactory): TransactionRepository {
        return TransactionRepository(transactionDao, currencyDao = currencyDao, transactionPagingSource = transactionPagingSourceFactory, transactionMapper = TransactionMapperImpl())
    }

    @Provides
    @Singleton
    fun provideTransactionGoods(db: TransactionDatabaseProvider): TransactionGoodsListDao {
        return db.getTransactionGoodsListDao()
    }

    @Provides
    @Singleton
    fun provideOperationDao(db: TransactionDatabaseProvider): OperationDao {
        return db.getOperationDao()
    }

    @Provides
    @Singleton
    fun provideOperationCategoryDao(db: TransactionDatabaseProvider): OperationCategoryDao {
        return db.getOperationCategoryDao()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(): TransactionRemoteDataSource {
        return TransactionRemoteDataSource()
    }
    @Provides
    @Singleton
    fun provideTransactionDao(db:TransactionDatabaseProvider):TransactionDao{
        return db.getTransactionDao()
    }

}