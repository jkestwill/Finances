package com.jk.financehelper.di

import com.jk.transaction_database.transaction.dao.OperationCategoryDao
import com.jk.transaction_database.transaction.dao.OperationDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
class OperationModule {
    @Provides
    @Singleton
    fun provideOperationDao(db: TransactionDatabase): OperationDao {
        return db.getOperationDao()
    }

    @Provides
    @Singleton
    fun provideOperationCategoryDao(db: TransactionDatabase): OperationCategoryDao {
        return db.getOperationCategoryDao()
    }
}

