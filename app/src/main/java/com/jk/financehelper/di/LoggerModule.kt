package com.jk.financehelper.di

import com.jk.common_data.LoggerTags
import com.jk.common_data.logger
import com.jk.financehelper.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.logging.Logger
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoggerModule {
    @Provides
    @Singleton
    @Named(LoggerTags.SELECT_CATEGORY)
    fun provideSelectCategoryLogger(): Logger? {
        return logger(tag = LoggerTags.SELECT_CATEGORY, BuildConfig.DEBUG)
    }

    @Provides
    @Singleton
    @Named(LoggerTags.ADD_NEW_TRANSACTION)
    fun provideAddNewCategoryLogger(): Logger? {
        return logger(tag = LoggerTags.ADD_NEW_TRANSACTION, BuildConfig.DEBUG)
    }
}