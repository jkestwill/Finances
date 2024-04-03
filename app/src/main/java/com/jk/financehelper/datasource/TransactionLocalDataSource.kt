package com.jk.financehelper.datasource

import androidx.room.Transaction

import com.jk.financehelper.utils.Response
import com.jk.financehelper.utils.apiRequest
import com.jk.financehelper.utils.toOperationCategoryList
import com.jk.financehelper.utils.toTransactionGoodsList
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.TransactionDao
import com.jk.transaction_database.transaction.dao.TransactionGoodsListDao
import com.jk.transaction_database.transaction.preview.TransactionPreviewDatabase
import com.jk.transaction_database.transaction.relations.TransactionRelation
import java.time.LocalDateTime
import javax.inject.Inject


class TransactionLocalDataSource @Inject constructor(
    private val operationLocalDataSource: OperationLocalDataSource,
    private val transactionDao: TransactionDao,
    private val goodsDao: GoodsDao,
    private val transactionGoodsListDao: TransactionGoodsListDao
) {
    companion object {
        private const val TAG = "TransactionLocalDataSou"
    }

    @Transaction
    suspend fun addTransaction(transaction: TransactionRelation): Response<Unit> {
        return apiRequest {
            goodsDao.insert(transaction.goodsList)
            transactionGoodsListDao.insert(transaction.goodsList.toTransactionGoodsList(transaction.transaction.id))
            operationLocalDataSource.insertOperation(transaction.operation)
            transactionDao.insert(transaction.transaction)
        }
    }


    suspend fun getExpensesSum(dateStart: LocalDateTime, dateEnd: LocalDateTime,currencyTo:String): Response<Double> {
        return apiRequest {
            transactionDao.getExpenses(dateStart, dateEnd,currencyTo=currencyTo)
        }
    }

    suspend fun getTransactionByCategoryId(
        categoryId: String,
        sortBy: String,
        isAsc: Boolean
    ): Response<List<TransactionPreviewDatabase>> {
        return apiRequest {
            transactionDao.getTransactionPreviewListByCategoryId(
                categoryId,
                sortBy,
                isAsc
            )
        }
    }
}