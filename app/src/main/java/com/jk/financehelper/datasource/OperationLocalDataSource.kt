package com.jk.financehelper.datasource

import androidx.room.Transaction

import com.jk.financehelper.utils.toOperationCategoryList
import com.jk.transaction_database.transaction.dao.CategoryDao
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.dao.OperationCategoryDao
import com.jk.transaction_database.transaction.dao.OperationDao
import com.jk.transaction_database.transaction.dao.ScheduleDao
import com.jk.transaction_database.transaction.relations.OperationRelation
import javax.inject.Inject

class OperationLocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao,
    private val moneyDao: MoneyDao,
    private val currencyDao: CurrencyDao,
    private val operationCategoryDao: OperationCategoryDao,
    private val scheduleDao: ScheduleDao,
    private val operationDao: OperationDao
) {
    @Transaction
    suspend fun insertOperation(operationRelation: OperationRelation) {
        categoryDao.insert(operationRelation.categoryList)
        currencyDao.insert(operationRelation.cost.currency)
        moneyDao.insert(operationRelation.cost.money)
        scheduleDao.insert(operationRelation.schedule)
        operationDao.insert(operationRelation.operation)
        operationCategoryDao.insert(
            operationRelation.categoryList.toOperationCategoryList(
                operationId = operationRelation.operation.id
            )
        )
    }
}