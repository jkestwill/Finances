package com.jk.financehelper.di


import com.jk.transaction_database.transaction.dao.GoodsDao
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
    fun provideTransactionGoods(db:TransactionDatabase): TransactionGoodsListDao {
        return db.getTransactionGoodsListDao()

    }
}