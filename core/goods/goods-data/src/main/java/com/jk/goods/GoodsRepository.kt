package com.jk.goods

import com.jk.transaction_database.transaction.datasource.GoodsLocalDataSource
import javax.inject.Inject

class GoodsRepository @Inject constructor (
    val goodsLocalDataSource: GoodsLocalDataSource
) {


    fun add(goodsList: List<Goods>){

    }

    fun getAll(){}

}