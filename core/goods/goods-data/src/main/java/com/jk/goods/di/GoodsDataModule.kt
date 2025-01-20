package com.jk.goods.di

import com.jk.goods.GoodsPagingSource
import com.jk.goods.GoodsRepository
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.LanguageDao
import com.jk.transaction_database.transaction.dao.LanguageMeasureListDao
import com.jk.transaction_database.transaction.dao.MeasureDao
import com.jk.transaction_database.transaction.database.TransactionDatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GoodsDataModule {

    @Provides
    @Singleton
    fun provideGoodsDao(db: TransactionDatabaseProvider): GoodsDao {
        return db.getGoodsDao()
    }

    @Provides
    @Singleton
    fun provideGoodsRepository(goodsDao: GoodsDao, goodsPagingSource: GoodsPagingSource.GoodsPagingSourceFactory): GoodsRepository {
        return GoodsRepository(goodsDao,goodsPagingSource)
    }

    @Provides
    @Singleton
    fun provideMeasureDao(db: TransactionDatabaseProvider): MeasureDao {
        return db.getMeasureDao()
    }

    @Provides
    @Singleton
    fun provideLanguageDao(db: TransactionDatabaseProvider): LanguageDao {
        return db.getLanguageDao()
    }

    @Provides
    @Singleton
    fun provideLanguageMeasureDao(db: TransactionDatabaseProvider): LanguageMeasureListDao {
        return db.getLanguageMeasureListDao()
    }

}