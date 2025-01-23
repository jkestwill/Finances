package com.jk.financehelper.di

import android.content.Context
import com.jk.common_data.Dispatchers
import com.jk.financehelper.BuildConfig
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
    fun provideDispatchers(): Dispatchers {
        return Dispatchers()
    }


}