package com.jk.money_account_data

import com.jk.common_data.ApiRequest
import com.jk.common_data.FinanceHelperException
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

    fun getAllFromDB(): Flow<ApiRequest<List<MoneyAccount>>> {
        val start = flowOf<ApiRequest<List<MoneyAccount>>>(ApiRequest.Loading())
        val result = flow<ApiRequest<List<MoneyAccount>>> {
            try {
                val result = moneyAccountDao.getAllDTO().map {
                    moneyAccountMapper.toMoneyAccount(it)
                }
                if (result.isEmpty()) {
                    emit(
                        ApiRequest.Error(
                            data = listOf(),
                            error = FinanceHelperException("List is empty", "money_acc_repo")
                        )
                    )
                } else {
                    emit(ApiRequest.Success(result))
                }
            } catch (e: IOException) {
                emit(ApiRequest.Error(null, FinanceHelperException("Database IO exception: ${e.message}", "money_acc_repo")))
            } catch (e: Exception) {
                emit(ApiRequest.Error(null, FinanceHelperException(e.message,"money_acc_repo")))
            }
        }
        return merge(start, result)
    }

    suspend fun insert(moneyAccount: MoneyAccount){
        moneyAccountDao.insert(moneyAccountMapper.toDTO(moneyAccount))
    }


}