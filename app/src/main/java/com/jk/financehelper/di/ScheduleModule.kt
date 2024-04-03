package com.jk.financehelper.di

import com.jk.transaction_database.transaction.dao.ScheduleDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ScheduleModule {

    @Provides
    @Singleton
    fun provideScheduleDao(db: TransactionDatabase): ScheduleDao {
        return db.getScheduleDao()
    }
}