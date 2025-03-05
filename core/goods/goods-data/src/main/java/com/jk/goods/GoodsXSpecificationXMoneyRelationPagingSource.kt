package com.jk.goods

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jk.goods.mapper.GoodsMapper
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.relations.GoodsxSpecificationsxMoneyRelation
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GoodsXSpecificationXMoneyRelationPagingSource @AssistedInject constructor(
    private val goodsRepository: GoodsDao,
    private val goodsMapper: GoodsMapper,
    @Assisted("q") private val q: String,
    @Assisted("sortBy") private var sortBy: String,
    @Assisted("isAsc") private var isAsc: Boolean
) : PagingSource<Int, GoodsxSpecificationsxMoneyRelation>() {
    override fun getRefreshKey(state: PagingState<Int, GoodsxSpecificationsxMoneyRelation>): Int? {
        val anchorPos = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPos) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GoodsxSpecificationsxMoneyRelation> {
        val pageSize = params.loadSize.coerceAtMost(20)
        val page = params.key ?: 0
        val result = goodsRepository.getAll(
            q = q,
            sortBy = sortBy,
            isAsc = isAsc,
            limit = pageSize,
            offset = page * pageSize
        )

        return try {
            if (result.isNotEmpty()) {
                val nextKey = if (result.size < pageSize) null else page + 1
                val prevKey = if (page == 0) null else page - 1
                LoadResult.Page(
                    data = result,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception("Response is empty $result"))
            }
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface GoodsPagingSourceFactory{
        fun create(
            @Assisted("q")  q: String,
            @Assisted("sortBy") sortBy: String,
            @Assisted("isAsc")  isAsc: Boolean
        ):GoodsXSpecificationXMoneyRelationPagingSource
    }
}


