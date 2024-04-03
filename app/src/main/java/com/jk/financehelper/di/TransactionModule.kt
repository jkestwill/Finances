package com.jk.financehelper.di

import com.jk.financehelper.main.TransactionRepository
import com.jk.financehelper.datasource.OperationLocalDataSource
import com.jk.financehelper.datasource.TransactionRemoteDataSource
import com.jk.financehelper.datasource.TransactionLocalDataSource
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.TransactionDao
import com.jk.transaction_database.transaction.dao.TransactionGoodsListDao
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
    fun provideTransactionLocalDataSource(
        transactionDao: TransactionDao,
        operationLocalDataSource: OperationLocalDataSource,
        goodsDao: GoodsDao,
        transactionGoodsListDao: TransactionGoodsListDao
    ): TransactionLocalDataSource {
        return TransactionLocalDataSource(
            operationLocalDataSource = operationLocalDataSource,
            transactionDao = transactionDao,
            goodsDao = goodsDao,
            transactionGoodsListDao = transactionGoodsListDao
        )
    }


    @Provides
    @Singleton
    fun provideTransactionRepository(
        localDataSource: TransactionLocalDataSource,
        dataSource: TransactionRemoteDataSource
    ): TransactionRepository {
        return TransactionRepository(localDataSource, dataSource)
    }
}