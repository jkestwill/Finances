package com.jk.exchange_rate_data

import android.content.res.Resources.NotFoundException
import com.example.currencyexchangeapi.services.by.NBRBApi
import com.example.currencyexchangeapi.services.by.NBRBApi.Companion.PARAM_MODE_2
import com.jk.common_data.ApiRequest
import com.jk.common_data.sha256
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.ExchangeRateDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NBRBRepository @Inject constructor(
    private val NBRBApi: NBRBApi,
    private val exchangeRateDao: ExchangeRateDao,
    private val currencyDao: CurrencyDao
) {
    fun getLast(
        currency: String,
        mergeStrategy: MergeStrategy<ApiRequest<ExchangeRate>> = ApiRequestMergeStrategy<ExchangeRate>()
    ): Flow<ApiRequest<ExchangeRate>> {
        val cache = getFromDatabase()
        val remoteResult = getFromApi(currency)
        return cache.combine(remoteResult) { dbResult, apiResult ->
            mergeStrategy.merge(dbResult, apiResult)
        }
    }

    fun fetchLatest(currency: String): Flow<ApiRequest<ExchangeRate>> {
        return getFromApi(currency)
    }

    private fun getFromApi(currency: String): Flow<ApiRequest<ExchangeRate>> {
        // сначала запись в бд потом емит
        val apiRequest: Flow<ApiRequest<ExchangeRate>> = flow {
            emit(NBRBApi.exchange(currency, PARAM_MODE_2))
        }.onEach {
            if (it.isSuccess) {
                val notNullApiRequest = checkNotNull(it.getOrThrow())

                currencyDao.insertIfNotExist(notNullApiRequest.currencyIn)
                currencyDao.insertIfNotExist(notNullApiRequest.currencyAbbreviation)

                val newIdHash = (notNullApiRequest.currencyIn + notNullApiRequest.date).sha256()
                val exRateDb = exchangeRateDao.getOrNull(newIdHash)
                if (exRateDb == null) {
                    exchangeRateDao.insert(
                        id = newIdHash,
                        currencyNameIn = notNullApiRequest.currencyIn,
                        currencyNameOut = notNullApiRequest.currencyAbbreviation,
                        date = notNullApiRequest.date,
                        scale = notNullApiRequest.scale,
                        rate = notNullApiRequest.rate
                    )
                }
                //  getOrNullExchangeRate((notNullApiRequest.currencyIn+notNullApiRequest.date).sha256(),notNullApiRequest)

            }
        }.map { result ->
            result.map {
                it.toExchangeRate()
            }.toApiRequest()
        }
        val startEmitFlow = flowOf(ApiRequest.Loading<ExchangeRate>())

        return merge(startEmitFlow, apiRequest)
    }

    private fun getFromDatabase(): Flow<ApiRequest<ExchangeRate>> {
        val dbRequest = flow<ApiRequest<ExchangeRate>> {
            val dbResult = exchangeRateDao.getRelevantExchangeList()
            val request: ApiRequest<ExchangeRate> = if (dbResult != null) {
                ApiRequest.Success(dbResult.toExchangeRate())
            } else ApiRequest.Error(
                data = dbResult,
                error = NotFoundException("The entity doesn't exist")
            )
            emit(request)
        }
        val startEmitFlow = flowOf(ApiRequest.Loading<ExchangeRate>())
        return merge<ApiRequest<ExchangeRate>>(startEmitFlow, dbRequest)
    }

}

internal fun Result<ExchangeRate>.toApiRequest(): ApiRequest<ExchangeRate> {
    return if (this.isSuccess) {
        ApiRequest.Success(getOrThrow())
    } else if (this.isFailure) {
        ApiRequest.Error()
    } else {
        error("Unknown state $this")
    }
}

