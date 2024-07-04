package com.jk.goods

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GoodsListViewModel @Inject constructor(
    val goodsRepository:GoodsRepository
) {

    val goodsStateFlow = MutableStateFlow<>()

    fun getAllGoods(){

    }
}