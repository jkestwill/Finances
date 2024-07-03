package com.jk.transaction_data.datasource

import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.dao.CategoryDao
import com.jk.transaction_database.transaction.datasource.LocalDataSource
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private var categoryDao: CategoryDao
) : LocalDataSource<TransactionCategoryDatabaseEntity, TransactionCategoryDatabaseEntity> {

    override suspend fun getById(id: String): TransactionCategoryDatabaseEntity? {
        return categoryDao.getById(id)

    }

    override suspend fun getAllItems(
        q:String,
        sortBy: String,
        isAsc: Boolean
    ): List<TransactionCategoryDatabaseEntity> {
        return categoryDao.getAll(q,sortBy, isAsc,0,0)

    }

    suspend fun update(t: TransactionCategoryDatabaseEntity) {
        return categoryDao.update(t)

    }

    override suspend fun add(t: TransactionCategoryDatabaseEntity):Long {
        return categoryDao.insert(t)

    }


}