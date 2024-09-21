package com.jk.financehelper.di

import android.content.Context
import com.example.currencyexchangeapi.model.NBRBApi
import com.jk.common_data.Dispatchers
import com.jk.financehelper.BuildConfig
import com.jk.transaction_database.transaction.dao.ExchangeRateDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.database.transactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNBRBApi(): NBRBApi {
        return NBRBApi(baseUrl = BuildConfig.NBRB_API_BASE_URL)
    }

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): TransactionDatabase =
        transactionDatabase(context,if(BuildConfig.DEBUG) BuildConfig.PREPOPULATE_DB_PATH else BuildConfig.PREPOPULATE_DB_PATH)


    @Provides
    @Singleton
    fun provideExchangeRateDao(transactionDatabase: TransactionDatabase): ExchangeRateDao {
        return transactionDatabase.getExchangeRateDao()
    }

    @Provides
    @Singleton
    fun provideDispatchers(): Dispatchers {
        return Dispatchers()
    }

    fun provideExchangeService(){

    }

}