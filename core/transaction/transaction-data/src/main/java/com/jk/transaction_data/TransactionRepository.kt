package com.jk.transaction_data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jk.transaction_database.transaction.dao.TransactionDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    val transactionDao: TransactionDao,
    val transactionPagingSource: TransactionPagingSourceFactory
) {
    fun getTransactionPreviewByCategoryId(
        categoryId: String,
        q: String,
        sortBy: String,
        isAsc: Boolean
    ): Flow<PagingData<TransactionPreview>> {
        return Pager(PagingConfig(20)) {
            transactionPagingSource.create(categoryId, q, sortBy, isAsc)
        }.flow
    }


    suspend fun addTransaction(transaction:Transaction){

    }
}

@AssistedFactory
interface TransactionPagingSourceFactory {

    fun create(
        @Assisted("categoryId") categoryId: String,
        @Assisted("q") q: String,
        @Assisted("sortBy") sortBy: String,
        @Assisted("isAsc") isAsc: Boolean
    ): TransactionPreviewPagingSource
}