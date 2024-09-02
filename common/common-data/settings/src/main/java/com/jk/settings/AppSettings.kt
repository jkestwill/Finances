package com.jk.settings

import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import java.io.InputStream
import java.io.OutputStream


//@Serializable
//data class AppSettings(
//    val isAnimeEnabled: Boolean,
//    val mainCurrency: String,
//    val mainCurrencyExchangeService: Int?,
//) {
//    companion object {
//        fun getDefault() = AppSettings(
//            isAnimeEnabled = false,
//            mainCurrency = "USD",
//            mainCurrencyExchangeService = null
//        )
//    }
//}
//
//object AppSettingsSerializer : Serializer<AppSettings> {
//    override val defaultValue: AppSettings = AppSettings.getDefault()
//    override suspend fun readFrom(input: InputStream): AppSettings {
//
//    }
//
//    override suspend fun writeTo(t: AppSettings, output: OutputStream) {

//
//    }
//}