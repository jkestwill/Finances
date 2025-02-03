package com.jk.goods

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jk.common_data.ApiRequest
import com.jk.common_data.SearchParams
import com.jk.common_goods_data.Goods
import com.jk.transaction_database.transaction.dao.GoodsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GoodsRepository @Inject constructor(
    private val goodsLocalDataSource: GoodsDao,
    private val goodsPagingSource: GoodsPagingSource.GoodsPagingSourceFactory,
    private val goodsMapper: GoodsMapper
) {
    suspend fun add(goodsList: List<Goods>) {
        for (i in goodsList) {
            //goodsLocalDataSource.insert(i.toGoodsRelation())
        }
    }

    suspend fun getByIdList(idList: List<String>): Flow<ApiRequest<List<Goods>>> {
        val start = flowOf(ApiRequest.Loading<List<Goods>>())
        val result: Flow<ApiRequest<List<Goods>>> = flow<List<Goods>> {
            emit(goodsLocalDataSource.getByIdList(idList).map { goodsMapper.toGoods(it) })
        }.map { goodsList ->
            try {
                ApiRequest.Success(goodsList)
            } catch (e: Throwable) {
                ApiRequest.Error(goodsList, e)
            }
        }
       return merge(start, result)

    }


    fun getAllFromDatabase(
        searchParams: SearchParams
    ): Flow<PagingData<Goods>> {
        return Pager(PagingConfig(20)) {
            goodsPagingSource.create(
                sortBy = searchParams.sortBy,
                isAsc = searchParams.isAsc,
                q = searchParams.q
            )
        }.flow
    }
}