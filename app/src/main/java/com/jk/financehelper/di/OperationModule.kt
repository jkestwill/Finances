package com.jk.financehelper.di

import com.jk.transaction_database.transaction.dao.CategoryDao
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.dao.OperationCategoryDao
import com.jk.transaction_database.transaction.dao.OperationDao
import com.jk.transaction_database.transaction.dao.ScheduleDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.datasource.OperationLocalDataSource
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
    fun provideOperationDao(db:TransactionDatabase): OperationDao {
        return db.getOperationDao()
    }

    @Provides
    @Singleton
    fun provideOperationCategoryDao(db: TransactionDatabase): OperationCategoryDao {
        return db.getOperationCategoryDao()
    }

    @Provides
    @Singleton
    fun provideOperationLocalDataSource(
        categoryDao: CategoryDao,
        operationCategoryDao: OperationCategoryDao,
        moneyDao: MoneyDao,
        currencyDao: CurrencyDao,
        operationDao: OperationDao,
        scheduleDao: ScheduleDao
    ): OperationLocalDataSource {
        return OperationLocalDataSource  (
            categoryDao = categoryDao,
            moneyDao = moneyDao,
            currencyDao = currencyDao,
            operationCategoryDao = operationCategoryDao,
            operationDao = operationDao,
            scheduleDao = scheduleDao
        )
    }
}