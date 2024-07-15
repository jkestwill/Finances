package com.jk.transaction_data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jk.transaction_common_data.TransactionPreview
import com.jk.transaction_database.transaction.dao.TransactionDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


class TransactionPreviewPagingSource @AssistedInject constructor(
    private val transactionDao: TransactionDao,
    @Assisted("categoryId") private val categoryId: String,
    @Assisted("q") private val q: String,
    @Assisted("sortBy") private var sortBy: String,
    @Assisted("isAsc") private var isAsc: Boolean
) : PagingSource<Int, TransactionPreview>() {
    override fun getRefreshKey(state: PagingState<Int, TransactionPreview>): Int? {
        val anchorPos = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPos) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionPreview> {
        val pageSize = params.loadSize.coerceAtMost(20)
        val page = params.key ?: 0
        val list = transactionDao.getTransactionPreviewListByCategoryId(
            categoryId = categoryId,
            q = q,
            orderBy = sortBy,
            isAsc = isAsc,
            offset = pageSize * page,
            limit = pageSize
        )

        return if (list.isNotEmpty()) {
            val nextKey = if (list.size < pageSize) null else page + 1
            val prevKey = if (page == 0) null else page - 1

            val data = checkNotNull(list.map { it.toPreview() })
            LoadResult.Page(data, prevKey, nextKey)
        } else {
            LoadResult.Error(Exception(""))
        }
    }


}


