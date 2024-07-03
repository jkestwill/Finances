package com.jk.transaction_database.transaction.datasource

import androidx.room.Transaction
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.dao.TransactionDao
import com.jk.transaction_database.transaction.dao.TransactionGoodsListDao
import com.jk.transaction_database.transaction.preview.TransactionPreviewEntity
import com.jk.transaction_database.transaction.relations.TransactionRelation
import com.jk.transaction_database.transaction.toTransactionGoodsList
import java.time.LocalDateTime


class TransactionLocalDataSource(
    private val operationLocalDataSource: OperationLocalDataSource,
    private val transactionDao: TransactionDao,
    private val goodsDao: GoodsDao,
    private val transactionGoodsListDao: TransactionGoodsListDao
) {
    companion object {
        private const val TAG = "TransactionLocalDataSou"
    }

    @Transaction
    suspend fun addTransaction(transaction: TransactionRelation) {

        goodsDao.insert(transaction.operation.goodsList)
        transactionGoodsListDao.insert(
            transaction.operation.goodsList.toTransactionGoodsList(
                transaction.transaction.id
            )
        )
        operationLocalDataSource.insertOperation(transaction.operation)
        transactionDao.insert(transaction.transaction)

    }


    fun getExpensesSum(
        dateStart: LocalDateTime,
        dateEnd: LocalDateTime,
        currencyTo: String
    ): Double {
        return transactionDao.getExpenses(dateStart, dateEnd, currencyTo = currencyTo)
    }

    suspend fun getTransactionByCategoryId(
        categoryId: String,
        sortBy: String,
        isAsc: Boolean,
        q: String = "",
        limit: Int = Int.MAX_VALUE,
        offset: Int = 0
    ): List<TransactionPreviewEntity> {
        return transactionDao.getTransactionPreviewListByCategoryId(
            categoryId = categoryId,
            orderBy = sortBy,
            q = q,
            isAsc = isAsc,
            limit = limit,
            offset = offset
        )

    }
}