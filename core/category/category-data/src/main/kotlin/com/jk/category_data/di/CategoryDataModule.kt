package com.jk.category_data.di

import androidx.compose.ui.graphics.Color
import com.jk.category_data.s.CategoryMapper
import com.jk.category_data.s.CategoryMapperImpl
import com.jk.common_ui.colorPickList
import com.jk.transaction_database.transaction.dao.CategoryDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CategoryDataModule {

    @Singleton
    @Provides
    fun provideCategoryDao(db: TransactionDatabaseProvider): CategoryDao {
        return db.getCategoryDao()
    }

    @Provides
    @Singleton
    fun provideColorList(): List<Color> {
        return colorPickList
    }

    @Provides
    fun provideCategoryMapper(): CategoryMapper {
        return CategoryMapperImpl()
    }
}