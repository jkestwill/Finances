package com.jk.exchange_rate_data

import com.example.currencyexchangeapi.ExchangeRateApiRequest
import com.example.currencyexchangeapi.ExchangeRateRequestParams
import com.example.currencyexchangeapi.ExchangeRateServices
import com.example.currencyexchangeapi.ExchangeServiceFactory
import com.jk.common_data.ApiRequest
import com.jk.common_data.map
import com.jk.money_common_data.ExchangeRate
import com.jk.transaction_database.transaction.dao.BankDao
import com.jk.transaction_database.transaction.dao.ExchangeRateDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import java.time.LocalDateTime
import javax.inject.Inject


class CurrencyExchangeRepository @Inject constructor(
    private val currencyExchangeServiceFactory: ExchangeServiceFactory,
    private val exchangeRateDao: ExchangeRateDao,
    private val bankDao: BankDao,
    // мб присобачить в апп модуль
    //private val appSettings: AppSettings
){
    fun getExchangeRate(exchangeRateServiceName:String,exchangeRateRequestParams: ExchangeRateRequestParams, mergeStrategy: ApiRequestMergeStrategy<ExchangeRate>): Flow<ApiRequest<ExchangeRate>> {
        val apiResult = fetchFromApi(exchangeRateRequestParams, ExchangeRateServices.getByName(exchangeRateServiceName))
        val dbResult = getFromDatabase(exchangeRateRequestParams)
        return dbResult.combine(apiResult,mergeStrategy::merge)
    }

    private fun fetchFromApi(exchangeRateRequestParams: ExchangeRateRequestParams, exchangeRateServices: ExchangeRateServices): Flow<ApiRequest<ExchangeRate>> {
        val start = flowOf(ApiRequest.Loading<ExchangeRate>())

        val currencyService  = currencyExchangeServiceFactory.create(exchangeRateServices)
        val result  = flow<ExchangeRateApiRequest> {
            currencyService.exchange( exchangeRateRequestParams)
        }.map {
            ApiRequest.Success(ExchangeRate("","",0,0.0, LocalDateTime.now(),null))
        }.catch {th->
            ApiRequest.Error(data = null,th)
        }
            //.map {
//            val exRate = it.data
//            val bank = exRate.bankName?.let {bankName->
//                 bankDao.getBankRelationByName(bankName)
//            }
////           val exRelation = ExchangeRateRelation(
////                ExchangeRateEntity( id="${exRate.rate}${exRate.currencyOut}${exRate.currencyIn}${exRate.scale}${exRate.date}".sha256(),
////                    currencyFromId = exRate.currencyIn,
////                    currencyToId = exRate.currencyOut,
////                    date = exRate.date,
////                    scale = exRate.scale,
////                    rate=exRate.rate,
////                    bankId = exRate.bankName?:"",
////                    ),
//               currencyTo = CurrencyEntity(id=exRate.currencyIn.sha256(),exRate.currencyIn),
//               currencyFrom =  CurrencyEntity(id=exRate.currencyOut.sha256(),exRate.currencyOut),
//               bankRelation = bank
//           )
//            exchangeRateDao.insertRelation(exRelation)
//            it.map {
//                exRate.toExchangeRate(bank = bank?.toBank())
//            }
        //}
        return merge(start,result)
    }


//merge
    private fun getFromDatabase(exchangeRateRequestParams: ExchangeRateRequestParams): Flow<ApiRequest<ExchangeRate>> {
        val result: Flow<ApiRequest<ExchangeRate>> = flow<ExchangeRate> {
           // emit(exchangeRateDao.getList(exchangeRateRequestParams.currencyIn,exchangeRateRequestParams.currencyOut,exchangeRateRequestParams.date))
        }.map {list->
            ApiRequest.Success(list)
        }.catch {
            ApiRequest.Error(null,it)
        }
        val start = flowOf(ApiRequest.Loading<ExchangeRate>())
        return merge(start,result)
    }

}