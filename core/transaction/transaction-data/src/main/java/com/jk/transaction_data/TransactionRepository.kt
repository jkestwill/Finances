package com.jk.transaction_data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jk.common_data.ApiRequest
import com.jk.transaction_common_data.Transaction
import com.jk.transaction_common_data.TransactionPreview
import com.jk.transaction_database.transaction.datasource.TransactionLocalDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    val transactionDao: TransactionLocalDataSource,
    private val transactionPagingSource: TransactionPagingSourceFactory
) {
    fun getTransactionPreviewByCategoryId(
        categoryId: String,
        q: String,
        sortBy: String,
        isAsc: Boolean
    ): Flow<ApiRequest<PagingData<TransactionPreview>>> {
        val startFlow = flowOf(ApiRequest.Loading<PagingData<TransactionPreview>>())
        val pagingFlow: Flow<ApiRequest<PagingData<TransactionPreview>>> =
            Pager(PagingConfig(20)) {
                transactionPagingSource.create(categoryId=categoryId,sortBy = sortBy, isAsc = isAsc, q = q)
            }.flow.map<PagingData<TransactionPreview>, ApiRequest<PagingData<TransactionPreview>>> {
                ApiRequest.Success(it)
            }.catch {
                emit(ApiRequest.Error<PagingData<TransactionPreview>>(data = null, error = it))
            }

        return merge(startFlow, pagingFlow)
    }


    suspend fun addTransaction(transaction: Transaction){
        //transactionDao.addTransaction()
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