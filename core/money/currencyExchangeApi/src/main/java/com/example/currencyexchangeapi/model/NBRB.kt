package com.example.currencyexchangeapi.model

import com.example.currencyexchangeapi.ExchangeRateApiRequest
import com.example.currencyexchangeapi.ExchangeRateMapResponse
import com.example.currencyexchangeapi.ExchangeRateRequestParams
import com.example.currencyexchangeapi.ExchangeRateService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.invoke
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NBRBApi(val httpClient: HttpClient,val baseUrl: String): ExchangeRateService {

    override suspend fun exchange(exchangeRateRequestParams: ExchangeRateRequestParams):ExchangeRateApiRequest {
        val builder = HttpRequestBuilder{
            path(exchangeRateRequestParams.currencyOut)
            protocol = URLProtocol.HTTPS
            host="$baseUrl$PATH_RATE"
            parameters.append("parammode", PARAM_MODE_2.toString())
            parameters.append("ondate",exchangeRateRequestParams.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            parameters.append("periodicity", PERIODICITY_DAY.toString())
        }
        // unresolved address
        val response =  httpClient.get(builder=builder)
        when(response.status.value){
            in 200..299->{
                return response.body<NBRBExchangeResponse>().map()
            }
            else->throw ServerResponseException(response,"error")
        }
    }



    companion object {
        /** Трехзначный цифровой  код валюты в соответствии со стандартом ИСО 4217
         * */
        const val PARAM_MODE_1 = 1
        /**
         *Трехзначный буквенный код валюты (ИСО 4217)
         * */
        const val PARAM_MODE_2 = 2
        /**
         * Периодичность установления курса ежедневно
         * */
        const val PERIODICITY_DAY=0
        /**
         * Периодичность установления курса ежемесячно
         * */
        const val PERIODICITY_MONTH=1

        const val PATH_RATE="/rates"
        const val PATH_CURRENCIES="/currencies"
        const val NAME= "NBRB"
    }
}

@Serializable
data class NBRBExchangeResponse(
    @SerialName("Cur_ID")
    val currencyId:String,
    @SerialName("Cur_Abbreviation")
    val currencyAbbreviation:String,
    @SerialName("Cur_Scale")
    val scale:Int,
    @SerialName("Cur_OfficialRate")
    val rate:Double,
    //pattern yyy-MM-dd
    @SerialName("Date")
    val date: String,
    @SerialName("Cur_Name")
    val name:String
): ExchangeRateMapResponse<NBRBExchangeResponse> {
    override fun map(): ExchangeRateApiRequest {

        return ExchangeRateApiRequest(
            isCurrencyInOnly = true,
            currencyIn = "BYN",
            currencyOut = currencyAbbreviation,
            scale=scale,
            date = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
            rate = rate,
            bankName = NBRBApi.NAME
        )
    }

}