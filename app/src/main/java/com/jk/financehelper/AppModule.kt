package com.jk.financehelper

import android.content.Context
import androidx.room.Room
import com.example.currencyexchangeapi.NBRBApi
import com.example.currencyexchangeapi.services.by.NBRBApi
import com.jk.transaction_database.transaction.dao.ExchangeRateDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
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
        Room.databaseBuilder(
            context = context, TransactionDatabase::class.java, name = "transaction"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideExchangeRateDao(transactionDatabase: TransactionDatabase): ExchangeRateDao {
        return transactionDatabase.getExchangeRateDao()
    }
}