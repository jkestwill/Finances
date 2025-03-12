package com.jk.money_account_data

import com.jk.common_data.ApiRequest
import com.jk.money_common_data.MoneyAccount
import com.jk.transaction_database.transaction.dao.MoneyAccountDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import java.io.IOException
import javax.inject.Inject

class MoneyAccountRepository @Inject constructor(
    private val moneyAccountDao: MoneyAccountDao,
    private val moneyAccountMapper: MoneyAccountMapper
) {

    fun getAll(): Flow<ApiRequest<List<MoneyAccount>>> {
        val start = flowOf<ApiRequest<List<MoneyAccount>>>(ApiRequest.Loading())
        val result = flow<ApiRequest<List<MoneyAccount>>> {
            try {
                ApiRequest.Success(moneyAccountDao.getAllDTO().map {
                    moneyAccountMapper.toMoneyAccount(it)
                })
            } catch (e: IOException) {
                ApiRequest.Error(e.message)
            }
        }
        return merge(start, result)
    }

}