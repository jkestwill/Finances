package com.jk.goods

import com.jk.common_data.ApiRequest
import com.jk.common_data.SearchParams
import com.jk.common_data.map
import com.jk.transaction_database.transaction.dao.GoodsDao
import com.jk.transaction_database.transaction.relations.GoodsRelation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class GoodsRepository @Inject constructor(
    private val goodsLocalDataSource: GoodsDao
) {


    suspend fun add(goodsList: List<com.jk.common_goods_data.Goods>) {
        for (i in goodsList) {
            goodsLocalDataSource.insert(i.toGoodsRelation())
        }
    }

    suspend fun getAllFromDatabase(
        searchParams: SearchParams,
        offset: Int = 0,
        limit: Int = 10
    ): Flow<ApiRequest<List<com.jk.common_goods_data.Goods>>> {
        val startFlow: Flow<ApiRequest<List<GoodsRelation>>> =
            flowOf(ApiRequest.Loading())

        val dbRequest: Flow<ApiRequest<List<GoodsRelation>>> =
            flowOf(
                goodsLocalDataSource.getAll(
                    searchParams.q,
                    searchParams.sortBy,
                    searchParams.isAsc,
                    offset,
                    limit
                )
            ).map {
                ApiRequest.Success(it)
            }.catch {
                ApiRequest.Error<List<GoodsRelation>>(error = it)
            }

        return merge(startFlow, dbRequest).map { request ->
            request.map { s -> s.map { it.toGoods() } }
        }
    }
}