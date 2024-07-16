package com.jk.transaction_data.datasource

import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.dao.CategoryDao
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private var categoryDao: CategoryDao
) {

    suspend fun getById(id: String): TransactionCategoryDatabaseEntity? {
        return categoryDao.getById(id)

    }

     suspend fun getAllItems(
        q:String,
        sortBy: String,
        isAsc: Boolean
    ): List<TransactionCategoryDatabaseEntity> {
        return categoryDao.getAll(q,sortBy, isAsc,0,20)

    }

    suspend fun update(t: TransactionCategoryDatabaseEntity) {
        return categoryDao.update(t)

    }

     suspend fun add(t: TransactionCategoryDatabaseEntity):Long {
        return categoryDao.insert(t)

    }


}