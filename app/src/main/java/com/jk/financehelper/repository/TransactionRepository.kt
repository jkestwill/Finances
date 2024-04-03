package com.jk.financehelper.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.jk.financehelper.datasource.TransactionLocalDataSource
import com.jk.financehelper.di.TransactionPreviewPagingSourceFactory
import com.jk.financehelper.domain.model.Transaction
import com.jk.financehelper.domain.model.preview.TransactionPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionPreviewSourceFactory: TransactionPreviewPagingSourceFactory,
    private val transactionLocalDataSource: TransactionLocalDataSource
) {


     fun getTransactionPreviewListByCategoryId(
        categoryId: String,
        sortBy: String,
        isAsc: Boolean
    ): Flow<PagingData<TransactionPreview>> =
        Pager(PagingConfig(10)) {
            transactionPreviewSourceFactory.create(
                categoryId = categoryId,
                sortBy = sortBy,
                isAsc = isAsc
            )
        }.flow

    fun add(transaction:Transaction){

    }

}