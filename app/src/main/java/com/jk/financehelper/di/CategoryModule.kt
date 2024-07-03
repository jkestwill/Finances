package com.jk.financehelper.di


import androidx.compose.ui.graphics.Color
import com.jk.transaction_data.datasource.CategoryLocalDataSource
import com.jk.financehelper.ui.theme.colorPickList
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
    fun provideCategoryLocalDataSource(categoryDao:CategoryDao): com.jk.transaction_data.datasource.CategoryLocalDataSource {
        return com.jk.transaction_data.datasource.CategoryLocalDataSource(categoryDao)
    }
    @Provides
    @Singleton
    fun provideCategoryDao(db: TransactionDatabase): CategoryDao {
        return db.getCategoryDao()
    }
    @Provides
    @Singleton
    fun provideColorList(): List<Color> {
        return colorPickList
    }
}