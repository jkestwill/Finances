package com.jk.transaction_data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jk.transaction_common_data.TransactionPreview
import com.jk.transaction_data.toPreview
import com.jk.transaction_database.transaction.datasource.TransactionLocalDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow

class TransactionPagingLocalSource @AssistedInject constructor (
    private val transactionLocalDataSource: TransactionLocalDataSource,
    @Assisted("sortBy")
    val sortBy:String,
    @Assisted("isAsc")
    val isAsc:Boolean,
    @Assisted("categoryId")
    val categoryId:String
):PagingSource<Int, TransactionPreview>() {

    private val transactionFlow = MutableStateFlow<List<TransactionPreview>>(listOf())

    override fun getRefreshKey(state: PagingState<Int, TransactionPreview>): Int? {
        val anchorPos = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPos) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionPreview> {
        val response =transactionLocalDataSource.getTransactionByCategoryId(categoryId=categoryId,sortBy = sortBy, isAsc = isAsc)
        val page = params.key ?: 1
        val pageSize = params.loadSize.coerceAtMost(20)

        return if (response.isNotEmpty()) {
            val data= response.map { s -> s.toPreview() }

            val nextKey = if (data.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
        } else {
            LoadResult.Error(Exception(""))
        }
    }
}