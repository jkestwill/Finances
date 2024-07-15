package com.jk.category_data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jk.category_common_data.TransactionCategory
import com.jk.transaction_database.transaction.dao.CategoryDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class CategoryPagingSource @AssistedInject constructor(
    private var categoryDao: CategoryDao,
    @Assisted("q") private val q: String,
    @Assisted("sortBy") private var sortBy: String,
    @Assisted("isAsc") private var isAsc: Boolean
) : PagingSource<Int, TransactionCategory>() {

    override fun getRefreshKey(state: PagingState<Int, TransactionCategory>): Int? {
        val anchorPos = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPos) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionCategory> {
        val pageSize = params.loadSize.coerceAtMost(20)
        val page = params.key ?: 0
        val response = categoryDao.getAll(
            q = q,
            sortBy = sortBy,
            isAsc = isAsc,
            limit = pageSize,
            offset = page * pageSize
        )

        return try {
            if (response.isNotEmpty()) {
                val nextKey = if (response.size < pageSize) null else page + 1
                val prevKey = if (page == 0) null else page - 1
                val data = checkNotNull(response.map { it.toCategory() })

                LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
            } else {
                LoadResult.Error(Exception("Response is empty $response"))
            }
        } catch (e: Throwable) {
            LoadResult.Error(Exception(e))
        }
    }
}