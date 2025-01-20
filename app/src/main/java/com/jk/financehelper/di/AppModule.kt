package com.jk.financehelper.di

import com.jk.common_data.Dispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {







    @Provides
    @Singleton
    fun provideDispatchers(): Dispatchers {
        return Dispatchers()
    }

}