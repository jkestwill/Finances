package com.jk.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class AppSettingsRepository(
    private val context:Context
) {
    private val appSettingsDataStore = context.appSettingsStore
    val appSettingsFlow = context.appSettingsStore.data.map {
        it.toDefault()
    }.catch {
        if(it is IOException){
            emit(AppSettingsProto.getDefaultInstance().toDefault())
        }else {
            throw it
        }
    }

    suspend fun setIsAnimeEnabled(isAnimeEnabled:Boolean){
        appSettingsDataStore.updateData{
            it.toBuilder().setIsAnimeEnabled(isAnimeEnabled).build()
        }
    }

    suspend fun setLocale(locale: Locale){
        appSettingsDataStore.updateData{
            it.toBuilder().setLocale(locale.toProto()).build()
        }
    }

    suspend fun setBankConfig(bankConfig: BankConfig){
        appSettingsDataStore.updateData{
           val currencyConfig = it.currencyConfig.toBuilder().setBankConfig(bankConfig.toProto()).build()
            it.toBuilder().setCurrencyConfig(currencyConfig).build()
        }
    }

    suspend fun setCurrencyConfig(currencyConfig: CurrencyConfig){
        appSettingsDataStore.updateData {
            it.toBuilder().setCurrencyConfig(currencyConfig.toProto()).build()
        }
    }
    suspend fun setSecurity(security: Security){
        appSettingsDataStore.updateData {
            it.toBuilder().setSecurity(security.toProto()).build()
        }
    }
}


private val APP_SETTINGS_NAME="app_settings"
private val DATA_STORE_FILE_NAME="app_settings.pb"

val Context.appSettingsStore:DataStore<AppSettingsProto > by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = AppSettingsSerializer
)

object AppSettingsSerializer :Serializer<AppSettingsProto >{
    override val defaultValue: AppSettingsProto  = AppSettingsProto .getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppSettingsProto  {
        return AppSettingsProto .parseFrom(input)
    }

    override suspend fun writeTo(t: AppSettingsProto , output: OutputStream){
        t.writeTo(output)
    }


}