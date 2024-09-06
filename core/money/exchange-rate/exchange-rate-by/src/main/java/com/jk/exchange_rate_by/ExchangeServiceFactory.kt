package com.jk.exchange_rate_by

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.invoke
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import io.ktor.http.parameters
import io.ktor.http.path
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface ExchangeServiceFactory{

    fun create(exchangeService:ExchangeRateServices):ExchangeRateService
}
class ExchangeServiceBY(val httpClient: HttpClient):ExchangeServiceFactory{
    override fun create(exchangeService:ExchangeRateServices): ExchangeRateService {
      if(exchangeService is ExchangeRateServices.BY){
          when(exchangeService){
              ExchangeRateServices.BY.NBRB->{
                  return NBRBApi(httpClient)
              }
              ExchangeRateServices.BY.DEFAULT->{

              }
          }

      }else{
          throw IllegalArgumentException("Illegal argument ${exchangeService}")
      }
    }

    fun getBankList():List<Bank>{
        return listOf()
    }
}

data class Bank(
    val name:String,
    val shortName:String?=null,
    val imageUrl:String?=null,
    val countryCode:String?=null
)
 sealed interface ExchangeRateServices{
     enum class BY:ExchangeRateServices{
         NBRB,DEFAULT
     }
     enum class RU:ExchangeRateServices{
         DEFAULT
     }

     enum class EU:ExchangeRateServices{
         DEFAULT
     }
}

interface ExchangeRateService{
  suspend fun exchange(exchangeRateRequestParams:ExchangeRateRequestParams):List<ExchangeRate>?
}

data class ExchangeRateRequestParams(
    val currencyIn:String,
    val currencyOut: String,
    val date:LocalDateTime
)

// change database
// isCurrencyInOnly - только одна валюта обмен которой можно посмотреть
// например у бнрб только белруб по отношениям к другим валютам
data class ExchangeRate(
    val isCurrencyInOnly:Boolean?=null,
    val currencyIn:String,
    val currencyOut:String,
    val scale:Int,
    val rate:Double,
    val date: LocalDateTime
){
}

class NBRBApi(val httpClient: HttpClient,val baseUrl:String):ExchangeRateService{



    override suspend fun exchange(exchangeRateRequestParams: ExchangeRateRequestParams):List<ExchangeRate> {
        val builder = HttpRequestBuilder{
            headers {
                append(HttpHeaders.Accept,"application/json")
                append(HttpHeaders.AcceptCharset,"UTF-8")
                append(HttpHeaders.AcceptEncoding,"gzip,deflate,sdch")

            }
            path(exchangeRateRequestParams.currencyOut)
            protocol = URLProtocol.HTTPS
            host="$baseUrl$PATH_RATE"
            parameters {
                append("parammode", PARAM_MODE_2.toString())
                append("ondate",exchangeRateRequestParams.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            }
        }

        val response =  httpClient.get(builder=builder)
        when(response.status.value){
             in 200..299->{
             return  response.body<List<NBRBExchangeResponse>>().map{it.map()}
            }
            else->throw ServerResponseException(response,"err")
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

        const val PATH_RATE="/rates"
        const val PATH_CURRENCIES="/currencies"
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
):ExchangeRateMapResponse<NBRBExchangeResponse> {
    override fun map(): ExchangeRate {
       return ExchangeRate(
           isCurrencyInOnly = true,
           currencyIn = "BYN",
           currencyOut = currencyAbbreviation,
           scale=scale,
           date = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yy-MM-dd")),
           rate = rate
       )
    }

}

interface ExchangeRateMapResponse<I>:MapResponse<I,ExchangeRate>{
    override fun map():ExchangeRate
}

interface MapResponse<I,O>{
    fun map():O
}