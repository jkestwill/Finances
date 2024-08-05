package com.jk.goods

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jk.common_data.ApiRequest
import com.jk.common_data.SearchParams
import com.jk.transaction_database.transaction.dao.GoodsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoodsRepository @Inject constructor(
    private val goodsLocalDataSource: GoodsDao,
    private val goodsPagingSource: GoodsPagingSource.GoodsPagingSourceFactory
) {


    suspend fun add(goodsList: List<com.jk.common_goods_data.Goods>) {
        for (i in goodsList) {
            goodsLocalDataSource.insert(i.toGoodsRelation())
        }
    }


    fun getAllFromDatabase(
        searchParams: SearchParams
    ): Flow<PagingData<com.jk.common_goods_data.Goods>> {
        return Pager(PagingConfig(20)) {
            goodsPagingSource.create(
                sortBy = searchParams.sortBy,
                isAsc = searchParams.isAsc,
                q = searchParams.q
            )
        }.flow
    }
}