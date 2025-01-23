package com.jk.transaction_database.transaction.datasource

import com.jk.common_data.Response
import com.jk.transaction_database.transaction.CategoryEntity

interface LocalDataSource<G, I> {

    suspend fun getById(id: String): Response<CategoryEntity?>

    suspend fun getAllItems(q:String,sortBy: String, isAsc: Boolean): Response<List<G>>

    suspend fun add(t: I): Response<Unit>


}