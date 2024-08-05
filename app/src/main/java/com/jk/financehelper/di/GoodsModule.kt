package com.jk.financehelper.di


import com.jk.goods.GoodsPagingSource
import com.jk.goods.GoodsRepository
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.LanguageDao
import com.jk.transaction_database.transaction.dao.LanguageMeasureListDao
import com.jk.transaction_database.transaction.dao.MeasureDao
import com.jk.transaction_database.transaction.dao.TransactionGoodsListDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GoodsModule {
    @Provides
    @Singleton
    fun provideGoods(db: TransactionDatabase): GoodsDao {
        return db.getGoodsDao()

    }

    @Provides
    @Singleton
    fun provideTransactionGoods(db: TransactionDatabase): TransactionGoodsListDao {
        return db.getTransactionGoodsListDao()
    }

    @Provides
    @Singleton
    fun provideMeasureDao(db: TransactionDatabase): MeasureDao {
        return db.getMeasureDao()
    }

    @Provides
    @Singleton
    fun provideLanguageDao(db: TransactionDatabase): LanguageDao {
        return db.getLanguageDao()
    }

    @Provides
    @Singleton
    fun provideLanguageMeasureDao(db: TransactionDatabase): LanguageMeasureListDao {
        return db.getLanguageMeasureListDao()
    }

    @Provides
    @Singleton
    fun provideGoodsRepository(goodsDao: GoodsDao,goodsPagingSource: GoodsPagingSource.GoodsPagingSourceFactory): GoodsRepository {
        return GoodsRepository(goodsDao,goodsPagingSource)
    }
}