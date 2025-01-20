package com.jk.settings.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.jk.settings.AppSettingsProto
import com.jk.settings.AppSettingsRepository
import com.jk.settings.Settings
import com.jk.settings.appSettingsStore
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {

    @Singleton
    @Provides
    fun provideSettingsRepository(@ApplicationContext context:Context): AppSettingsRepository {
        return AppSettingsRepository(context.applicationContext)
    }

}
