package com.jk.financehelper.main

import com.jk.financehelper.datasource.TransactionLocalDataSource
import com.jk.financehelper.datasource.TransactionRemoteDataSource
import com.jk.financehelper.utils.Response
import com.jk.financehelper.utils.apiRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import javax.inject.Inject

// если создать сервер то сливать и выбирать свежее
class TransactionRepository @Inject constructor(
    private var transactionLocalDataCourse: TransactionLocalDataSource,
    private var transactionRemoteDataSource: TransactionRemoteDataSource
) {
    suspend fun getExpensesSum(
        dateStart: LocalDateTime,
        dateEnd: LocalDateTime,
        currency: String
    ): Flow<Response<Double>> {
        return flowOf(
            transactionLocalDataCourse.getExpensesSum(
                dateStart = dateStart,
                dateEnd = dateEnd,
               currency
            )
        )
    }
}