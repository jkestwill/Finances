package com.jk.financehelper.datasource

import com.jk.financehelper.utils.Response
import com.jk.transaction_database.transaction.TransactionCategoryDatabaseEntity

interface LocalDataSource<G, I> {

    suspend fun getById(id: String): Response<TransactionCategoryDatabaseEntity?>

    suspend fun getAllItems(sortBy: String, isAsc: Boolean): Response<List<G>>

    suspend fun add(t: I): Response<Unit>


}