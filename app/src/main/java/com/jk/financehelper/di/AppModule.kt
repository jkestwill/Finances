package com.jk.financehelper.di

import com.jk.common_data.DispatcherProvider
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
    fun provideDispatchers(): DispatcherProvider {
        return DispatcherProvider()
    }


}