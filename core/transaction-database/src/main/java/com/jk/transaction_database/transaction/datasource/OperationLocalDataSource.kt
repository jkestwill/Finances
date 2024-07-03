package com.jk.transaction_database.transaction.datasource

import androidx.room.Transaction
import com.jk.transaction_database.transaction.dao.CategoryDao
import com.jk.transaction_database.transaction.dao.CurrencyDao
import com.jk.transaction_database.transaction.dao.MoneyDao
import com.jk.transaction_database.transaction.dao.OperationCategoryDao
import com.jk.transaction_database.transaction.dao.OperationDao
import com.jk.transaction_database.transaction.dao.ScheduleDao
import com.jk.transaction_database.transaction.database.TransactionDatabase
import com.jk.transaction_database.transaction.relations.OperationRelation
import com.jk.transaction_database.transaction.toOperationCategoryList


class OperationLocalDataSource(
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

fun createOperationLocalDatasource(db: TransactionDatabase): OperationLocalDataSource {
    return OperationLocalDataSource(
        categoryDao = db.getCategoryDao(),
        moneyDao = db.getMoneyDao(),
        currencyDao = db.getCurrencyDao(),
        operationDao = db.getOperationDao(),
        operationCategoryDao = db.getOperationCategoryDao(),
        scheduleDao = db.getScheduleDao()
    )
}