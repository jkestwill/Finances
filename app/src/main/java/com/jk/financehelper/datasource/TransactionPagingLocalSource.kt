package com.jk.financehelper.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jk.financehelper.domain.model.preview.TransactionPreview
import com.jk.financehelper.utils.toPreview
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow

class TransactionPagingLocalSource @AssistedInject constructor (
    private val transactionLocalDataSource:TransactionLocalDataSource,
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

        return if (response.isSuccess()) {
            response.onSuccess {
               transactionFlow.emit(it.map { s -> s.toPreview() })
            }
            val nextKey = if (transactionFlow.value.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            val data = checkNotNull(transactionFlow.value)

            LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
        } else {
            LoadResult.Error(Exception(""))
        }
    }
}