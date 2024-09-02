package com.jk.money_data

import com.jk.common_data.ApiRequest
import com.jk.money_common_data.Currency
import com.jk.transaction_database.transaction.TransactionCurrencyDatabaseEntity
import com.jk.transaction_database.transaction.dao.CurrencyDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

// создать промежуточный класс где стыкуются валюта и обменник
class CurrencyRepository @Inject constructor(
    private val currencyDao: CurrencyDao
) {
    fun getCurrencyList(): Flow<ApiRequest<List<Currency>>> {
        val startEmitFlow = flowOf(ApiRequest.Loading<List<Currency>>())
        val result: Flow<ApiRequest<List<Currency>>> = flow {
            emit(currencyDao.getAll())
        }.map {
            ApiRequest.Success(it.map { s -> s.toCurrency() })

        }.catch {
            ApiRequest.Error(data = null, error = it)
        }
        return merge(startEmitFlow, result)
    }
}