package com.jk.money_account_data.di

import com.jk.money_account_data.MoneyAccountMapper
import com.jk.money_account_data.MoneyAccountMapperImpl
import com.jk.money_account_data.MoneyAccountRepository
import com.jk.transaction_database.transaction.dao.MoneyAccountDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MoneyAccountModule {

    @Provides
    fun proivdeMoneyAccountRepository(moneyAccountDao: MoneyAccountDao,moneyAccountMapper:MoneyAccountMapper): MoneyAccountRepository {
        return MoneyAccountRepository(moneyAccountDao,moneyAccountMapper)
    }

    @Provides
    fun provideMoneyAccountMapper():MoneyAccountMapper{
        return MoneyAccountMapperImpl()
    }
}