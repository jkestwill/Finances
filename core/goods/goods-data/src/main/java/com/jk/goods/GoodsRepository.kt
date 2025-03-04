package com.jk.goods

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.jk.common_data.ApiRequest
import com.jk.common_data.SearchParams
import com.jk.common_goods_data.Goods
import com.jk.common_goods_data.GoodsPreview
import com.jk.transaction_database.transaction.dao.GoodsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import java.io.IOException
import javax.inject.Inject

class GoodsRepository @Inject constructor(
    private val goodsDao: GoodsDao,
    private val goodsPagingSource: GoodsXSpecificationXMoneyRelationPagingSource.GoodsPagingSourceFactory,
    private val goodsPreviewPagingSource: GoodsPreviewPagingSourceFactory,
    private val goodsMapper: GoodsMapper
) {
    suspend fun add(goodsList: List<Goods>) =
        goodsDao.insertGoodsRelation(goodsList.map {
            goodsMapper.toGoodsxSpecificationsxMoneyRelation(
                it
            )
        })

    suspend fun getByIdList(idList: List<String>): Flow<ApiRequest<List<Goods>>> {
        val start = flowOf(ApiRequest.Loading<List<Goods>>())
        val result: Flow<ApiRequest<List<Goods>>> = flow<List<Goods>> {
            emit(goodsDao.getByIdList(idList).map { goodsMapper.toGoods(it) })
        }.map { goodsList ->
            try {
                ApiRequest.Success(goodsList)
            } catch (e: Throwable) {
                ApiRequest.Error(goodsList, e)
            }
        }
        return merge(start, result)
    }

    fun getById(id: String): Flow<ApiRequest<Goods>> {
        val start = flowOf<ApiRequest<Goods>>(ApiRequest.Loading())
        val result = flow {
            emit(goodsMapper.toGoods(goodsDao.getGoodsxSpecificationById(id)))
        }.map {
            try {
                ApiRequest.Success(it)
            } catch (e: IOException) {
                ApiRequest.Error(it, e)
            }
        }
        return merge(start, result)
    }

    fun getGoodsPreviewList(searchParams: SearchParams): Flow<PagingData<GoodsPreview>> {
        return Pager(PagingConfig(20)) {
            goodsPreviewPagingSource.create(searchParams.q, searchParams.sortBy, searchParams.isAsc)
        }.flow.map { page -> page.map { goodsMapper.toPreview(it) } }
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
        }.flow.map { it.map { g -> goodsMapper.toGoods(g) } }
    }
}