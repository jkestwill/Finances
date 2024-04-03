package com.jk.financehelper.di


import com.jk.financehelper.datasource.CategoryLocalDataSource
import com.jk.transaction_database.transaction.dao.CategoryDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CategoryModule {

    @Provides
    @Singleton
    fun provideCategoryLocalDataSource(categoryDao:CategoryDao):CategoryLocalDataSource{
        return CategoryLocalDataSource(categoryDao)
    }
    @Provides
    @Singleton
    fun provideCategoryDao(db: TransactionDatabase): CategoryDao {
        return db.getCategoryDao()
    }
}