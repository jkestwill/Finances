package com.jk.financehelper.di


import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoneyModule {

    @Singleton
    @Provides
    fun provideMoneyDao(db: TransactionDatabase): MoneyDao {
        return db.getMoneyDao()
    }
}