package com.example.currencyexchangeapi

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.logging.Level

fun configureHttpClient():HttpClient{
    return HttpClient(CIO){
        install(ContentNegotiation){
            json(Json {
                prettyPrint=true
                isLenient=true
            })
        }
        install(Logging){
            level=LogLevel.BODY
        }
    }
}