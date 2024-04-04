package com.jk.financehelper.datasource

import com.jk.financehelper.utils.Response
import com.jk.financehelper.utils.apiRequest
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity
import com.jk.transaction_database.transaction.dao.CategoryDao
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private var categoryDao: CategoryDao
) : LocalDataSource<TransactionCategoryDatabaseEntity, TransactionCategoryDatabaseEntity> {

    override suspend fun getById(id: String): Response<TransactionCategoryDatabaseEntity?> {
        return apiRequest<TransactionCategoryDatabaseEntity?> {
            categoryDao.getById(id)
        }
    }

    override suspend fun getAllItems(
        q:String,
        sortBy: String,
        isAsc: Boolean
    ): Response<List<TransactionCategoryDatabaseEntity>> {
        return apiRequest<List<TransactionCategoryDatabaseEntity>> {
            categoryDao.getAll(q,sortBy, isAsc,0,0)
        }
    }

    suspend fun update(t: TransactionCategoryDatabaseEntity): Response<Unit> {
        return apiRequest {
            categoryDao.update(t)
        }
    }

    override suspend fun add(t: TransactionCategoryDatabaseEntity): Response<Unit> {
        return apiRequest {
            categoryDao.insert(t)
        }
    }


}