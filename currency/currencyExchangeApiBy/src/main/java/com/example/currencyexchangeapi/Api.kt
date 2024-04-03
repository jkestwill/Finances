package com.example.currencyexchangeapi

import com.example.currencyexchangeapi.model.BodyType
import com.example.currencyexchangeapi.services.by.NBRBApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.create

/**
 *  Api mark
 */

interface Api


fun NBRBApi(
    baseUrl: String,
): NBRBApi {
    return Api(baseUrl, NBRBApiHttpClientConfig(), BodyType.JSON)
}

fun NBRBApiHttpClientConfig(): OkHttpClient {
   return OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()
}

internal inline fun <reified T : Api> Api(
    baseUrl: String,
    okhttpClient: OkHttpClient? = null,
    contentType: String
): T {
    return when (contentType) {
        BodyType.JSON -> {
            retrofitJson(baseUrl, okhttpClient, Json).create<T>()
        }

        BodyType.XML -> {
            retrofitXml(baseUrl, okhttpClient).create<T>()
        }

        else -> {
            throw IllegalArgumentException("No such content type $contentType")
        }
    }

}


internal fun retrofitJson(
    baseUrl: String,
    okhttpClient: OkHttpClient? = null,
    json: Json
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .run {
            if (okhttpClient == null) this else client(okhttpClient)
        }
        .build()
}

internal fun retrofitXml(baseUrl: String, okhttpClient: OkHttpClient?): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .run {
            if (okhttpClient == null) this else client(okhttpClient)
        }
        .build()
}